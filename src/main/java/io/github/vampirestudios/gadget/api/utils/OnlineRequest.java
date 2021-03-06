package io.github.vampirestudios.gadget.api.utils;

import io.github.vampirestudios.gadget.util.StreamUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.Charset;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * OnlineRequest is a simple built in request system for handling URL connections.
 * It runs in the background so it doesn't freeze the user interface of your application.
 * All requests are returned with a string, use how you please!
 */
public class OnlineRequest {
    private static OnlineRequest instance = null;

    private final Queue<RequestWrapper> requests;

    private OnlineRequest() {
        this.requests = new ConcurrentLinkedQueue<>();
        start();
    }

    /**
     * Gets a singleton instance of OnlineRequest. Use this instance to
     * start making requests.
     *
     * @return the singleton OnlineRequest object
     */
    public static OnlineRequest getInstance() {
        if (instance == null) {
            instance = new OnlineRequest();
        }
        return instance;
    }

    private void start() {
        Thread thread = new Thread(new RequestRunnable(), "Online Request Thread");
        thread.start();
    }

    /**
     * Adds a request to the queue. Use the handler to process the
     * response you get from the URL connection.
     *
     * @param url     the URL you want to make a request to
     * @param handler the response handler for the request
     */
    public void make(String url, ResponseHandler handler) {
        synchronized (requests) {
            requests.offer(new RequestWrapper(url, handler));
            requests.notify();
        }
    }

    public interface ResponseHandler {
        /**
         * Handles the response from an OnlineRequest
         *
         * @param success  if the request was successful or not
         * @param response the response from the request. null if success is false
         */
        void handle(boolean success, String response);
    }

    private static class RequestWrapper {
        public final String url;
        public final ResponseHandler handler;

        RequestWrapper(String url, ResponseHandler handler) {
            this.url = url;
            this.handler = handler;
        }
    }

    private class RequestRunnable implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (requests) {
                    requests.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (!requests.isEmpty()) {
                RequestWrapper wrapper = requests.poll();
                try (CloseableHttpClient client = HttpClients.createDefault()) {
                    HttpGet get = new HttpGet(wrapper.url);
                    try (CloseableHttpResponse response = client.execute(get)) {
                        String raw = IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset());
                        System.out.println(raw);
                        wrapper.handler.handle(true, raw);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    wrapper.handler.handle(false, "");
                }
            }
        }
    }
}

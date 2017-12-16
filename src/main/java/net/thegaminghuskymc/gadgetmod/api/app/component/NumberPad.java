package net.thegaminghuskymc.gadgetmod.api.app.component;

import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;

public class NumberPad extends Component {

    private TextField amountField;

    public NumberPad(int left, int top) {
        super(left, top);
    }

    @Override
    protected void init(Layout layout) {

        amountField = new TextField(5, 45, 110);
        amountField.setText("0");
        amountField.setEditable(false);
        layout.addComponent(amountField);

        for (int i = 0; i < 9; i++) {
            int posX = 5 + (i % 3) * 19;
            int posY = 65 + (i / 3) * 19;
            Button button = new Button(posX, posY, Integer.toString(i + 1));
            button.setSize(16, 16);
            addNumberClickListener(button, amountField, i + 1);
            layout.addComponent(button);
        }
    }

    public void addNumberClickListener(Button btn, final TextField field, final int number) {
        btn.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) {
                if (!(field.getText().equals("0") && number == 0)) {
                    if (field.getText().equals("0"))
                        field.clear();
                    field.writeText(Integer.toString(number));
                }
            }
        });
    }

}

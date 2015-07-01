package org.hsbp.urc;

public enum Action {
    BTN0(      '0', R.id.btn0, false),
    BTN1(      '1', R.id.btn1, false),
    BTN2(      '2', R.id.btn2, false),
    BTN3(      '3', R.id.btn3, false),
    BTN4(      '4', R.id.btn4, false),
    BTN5(      '5', R.id.btn5, false),
    STOP(      'j', R.id.stop, false),
    UP(        'w', R.id.up, true),
    DOWN(      's', R.id.down, true),
    LEFT(      'a', R.id.left, true),
    RIGHT(     'd', R.id.right, true),
    HEAD_LEFT( 'q', R.id.head_left, true),
    HEAD_RIGHT('e', R.id.head_right, true);

    public final byte[] cmd;
    public final int res;
    public final boolean repeat;

    private Action(char cmd, int res, boolean repeat) {
        this.cmd = new byte[] { (byte)cmd };
        this.res = res;
        this.repeat = repeat;
    }
}

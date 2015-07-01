package org.hsbp.urc;

import java.net.*;
import java.util.*;
import java.io.IOException;

public class Sender extends Thread {
    public static final int DEFAULT_PAUSE = 100;
    public static final short PORT = 20752;
    private int pause = DEFAULT_PAUSE;
    private final DatagramSocket socket;
    private InetAddress address;
    private String host;
    private boolean hostChanged;
    private EnumSet<Action> queue = EnumSet.noneOf(Action.class);

    public Sender() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    public void setAddress(String host) {
        synchronized (socket) {
            this.host = host;
            hostChanged = true;
        }
    }

    public void setPause(int pause) {
        if (pause >= 10) this.pause = pause;
    }

    public void stop(Action a) {
        synchronized (queue) {
            queue.remove(a);
        }
    }

    public void send(Action a) {
        synchronized (queue) {
            queue.add(a);
        }
    }

    public void run() {
        Set<Action> todo = null;
        while (true) {
            try {
                Thread.sleep(pause);
            } catch (InterruptedException ie) {}
            synchronized (socket) {
                if (hostChanged) {
                    try {
                        address = InetAddress.getByName(host);
                        hostChanged = false;
                    } catch (UnknownHostException uhe) {
                        uhe.printStackTrace();
                    }
                }
                if (address == null) continue;
            }
            synchronized (queue) {
                if (queue.isEmpty()) continue;
                todo = queue.clone();
            }
            for (Action a : todo) {
                if (!a.repeat) synchronized (queue) { queue.remove(a); };
                final DatagramPacket p = new DatagramPacket(a.cmd, 1, address, PORT);
                synchronized (socket) {
                    try {
                        socket.send(p);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        }
    }
}

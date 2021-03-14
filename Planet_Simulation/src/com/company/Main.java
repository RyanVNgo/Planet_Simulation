package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Main extends Canvas implements Runnable  {

    private Thread thread;
    private final JFrame frame;
    private static final String title = "3D Renderer";
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static boolean running = false;

    private Mouse mouse;
    public Main() {
        this.frame = new JFrame();

        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        // For mouse control
        this.mouse = new Mouse();
        this.addMouseListener(this.mouse);
        this.addMouseMotionListener(this.mouse);
        this.addMouseWheelListener(this.mouse);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.frame.setTitle(title);
        main.frame.add(main);
        main.frame.pack();
        main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.frame.setLocationRelativeTo(null);
        main.frame.setResizable(false);
        main.frame.setVisible(true);

        main.start();
    }

    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "Display");
        this.thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final int UPS = Settings.GetUpdatesPerSecond();
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / UPS;
        double delta = 0;
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                update();
                render(); // !!!
                delta--;

            }

            //render(); // !!!
            frames++;

            /*
            // Frame Rate Counter
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.frame.setTitle(title + " | " + frames + " fps");
                frames = 0;
            }
            */

        }

        stop();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH, HEIGHT);

        // - - - - -

        ArrayList<CelestialBody> CBArray = GetCelestialBodies();

        // GetCamOffsets
        int xCamOffset = FunctionList.FindXCamOffset(CBArray);
        int yCamOffset = FunctionList.FindYCamOffset(CBArray);


        if (Settings.GetDrawDistLines()) {
            DrawDistLinesBetweenBodies(g, CBArray, xCamOffset, yCamOffset);
        }
        DrawCelestialBodies(g, CBArray, xCamOffset, yCamOffset);

        // - - - - -
        g.dispose();
        bs.show();
    }

    private void DrawCelestialBodies(Graphics g, ArrayList<CelestialBody> CBArray, int xCamOffset, int yCamOffset) {
        int c = 0;
        for (CelestialBody CB : CBArray) {
            int xPos = (int)CB.xPos;
            int yPos = (int)CB.yPos;
            int d = (int)CB.diam; // !!!
            int r = d/2;

            int xOffset = WIDTH/2 - r + xPos - xCamOffset;
            int yOffset = HEIGHT/2 - r - yPos + yCamOffset;

            g.setColor(CB.color);
            //g.drawOval(xOffset, yOffset, d, d);
            g.fillOval(xOffset, yOffset, d, d);

            // Draw data for each celestial body
            g.setColor(CB.color);
            String namStr = CB.name;
            String velStr = String.format("Vel: (%.3f , %.3f)", CB.xVel, CB.yVel);
            String spdStr = String.format("Spd: %.3f", CB.speed);
            String accStr = String.format("Acc: (%.3f , %.3f)", CB.xAcc, CB.yAcc);
            String posStr = String.format("Pos: (%.3f , %.3f)", CB.xPos, CB.yPos);

            g.drawString(namStr, 0, 12 + (c * 60));
            g.drawString(velStr, 0, 24 + (c * 60));
            g.drawString(spdStr, 0, 36 + (c * 60));
            g.drawString(accStr, 0, 48 + (c * 60));
            g.drawString(posStr, 0, 60 + (c * 60));

            // Draw names above planets
            if (Settings.GetDrawNames()) {
                g.drawString(namStr, xOffset, yOffset - 5);
            }
            c++;
        }

    }
    private void DrawDistLinesBetweenBodies(Graphics g, ArrayList<CelestialBody> CBArray, int xCamOffset, int yCamOffset) {
        // Go through each body
        for (CelestialBody body : CBArray) {
            // Check for body name (Only draws line for center body)
            if (body.name.equals(Settings.GetBodyCenter())) {
                // Go through each body to draw line toward
                for (CelestialBody body2 : CBArray) {
                    // Ensure body doesn't draw line on itself
                    if (!body.name.equals(body2.name)) {
                        // Check if body2 is dead
                        if (!body2.dead) {
                            int xPosBody1 = (int) body.xPos;
                            int yPosBody1 = (int) body.yPos;
                            int xPosBody2 = (int) body2.xPos;
                            int yPosBody2 = (int) body2.yPos;

                            int xOffsetBody1 = WIDTH / 2 + xPosBody1 - xCamOffset;
                            int yOffsetBody1 = HEIGHT / 2 - yPosBody1 + yCamOffset;
                            int xOffsetBody2 = WIDTH / 2 + xPosBody2 - xCamOffset;
                            int yOffsetBody2 = HEIGHT / 2 - yPosBody2 + yCamOffset;

                            int xPos = (Math.abs(xOffsetBody1) - Math.abs(xOffsetBody2)) / -2;
                            int yPos = (Math.abs(yOffsetBody1) - Math.abs(yOffsetBody2)) / -2;
                            int pDist = (int) (2 * Math.sqrt(Math.pow(xPos, 2) + Math.pow(yPos, 2)));
                            int xPosStr = xPos + xOffsetBody1;
                            int yPosStr = yPos + yOffsetBody1;

                            g.setColor(Color.getHSBColor(0, 0, .25f));
                            Polygon line = new Polygon();
                            line.addPoint(xOffsetBody1, yOffsetBody1);
                            line.addPoint(xOffsetBody2, yOffsetBody2);
                            g.drawPolygon(line);
                            g.setColor(Color.getHSBColor(0, 0, .375f));
                            g.drawString(String.valueOf(pDist), xPosStr, yPosStr);
                        }
                    }
                }
            }
        }

    }

    private void UpdateAllCelestialBodies2() {
        ArrayList<CelestialBody> CBArray = GetCelestialBodies();

        // Go through all Celestial bodies
        for (int i = 0; i < CBArray.size(); i++) {
            CelestialBody mainBody = CBArray.get(i);
            // Determine if mainBody is alive and to proceed with calculation
            if (!mainBody.dead) {
                ArrayList<Double> xChangeArray = new ArrayList<>();
                ArrayList<Double> yChangeArray = new ArrayList<>();
                ArrayList<Double> xAccArray = new ArrayList<>();
                ArrayList<Double> yAccArray = new ArrayList<>();
                // Go through all bodies that effect main body
                for (CelestialBody refBody : CBArray) {
                    // Ensure program doesn't calculate body on itself
                    if (!mainBody.name.equals(refBody.name)) {
                        // Determine if refBody is alive and to proceed with calculation
                        if (!refBody.dead) {
                            double pDist = FunctionList.FindDistanceBetweenTwoBodies(mainBody, refBody);
                            double xForce = FunctionList.FindXForce(refBody, mainBody);
                            double yForce = FunctionList.FindYForce(refBody, mainBody);

                            mainBody.xVel = mainBody.xVel + xForce;
                            mainBody.yVel = mainBody.yVel + yForce;

                            double xChange = (.5 * xForce) + mainBody.xVel;
                            double yChange = (.5 * yForce) + mainBody.yVel;

                            xChangeArray.add(xChange);
                            yChangeArray.add(yChange);
                            xAccArray.add(xForce);
                            yAccArray.add(yForce);

                            if (pDist < refBody.diam / 2f) {
                                mainBody.dead = true;
                            }
                        }
                    }
                }

                // Calculate sum xChange and apply
                double sumXChange = 0;
                double sumYChange = 0;
                double sumXAcc = 0;
                double sumYAcc = 0;

                for (double xChange : xChangeArray) {
                    sumXChange = sumXChange + xChange;
                }
                for (double yChange : yChangeArray) {
                    sumYChange = sumYChange + yChange;
                }
                for (double xAcc : xAccArray) {
                    sumXAcc += xAcc;
                }
                for (double yAcc : yAccArray) {
                    sumYAcc += yAcc;
                }

                mainBody.xPos = mainBody.xPos + sumXChange;
                mainBody.yPos = mainBody.yPos + sumYChange;

                mainBody.UpdateSpeed();
                mainBody.xAcc = sumXAcc;
                mainBody.yAcc = sumYAcc;

            }
        }

    }

    // (String name, int mass, int diam, int xVel, int yVel, int xPos, int yPos, int[] color)
    private final CelestialBody pluto = new CelestialBody("Pluto", 1.309, 14.768, 0, 0, 0, 0, Color.LIGHT_GRAY);
    private final CelestialBody charon = new CelestialBody("Charon", .16, 7.531, 0.152, 0, 0, 122, Color.GRAY);

    private ArrayList<CelestialBody> GetCelestialBodies() {
        ArrayList<CelestialBody> celBodyArray = new ArrayList<>();
        celBodyArray.add(pluto);
        celBodyArray.add(charon);;

        return celBodyArray;
    }

    int initialX, initialY;
    private void update() {
        UpdateAllCelestialBodies2();

        // For frame dragging / mouse input (this is bad implementation but it works)  - - - -
        if (this.mouse.GetMouseB() == 1) {
            int xChange = initialX - this.mouse.GetMouseX();
            int yChange = initialY - this.mouse.GetMouseY();

            if (xChange < 10 && xChange > -10 && yChange < 10 && yChange > -10) {
                FunctionList.ChangeMouseXOffset(xChange);
                FunctionList.ChangeMouseYOffset(yChange);
            }
        }
        initialX = this.mouse.GetMouseX();
        initialY = this.mouse.GetMouseY();
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    }


}

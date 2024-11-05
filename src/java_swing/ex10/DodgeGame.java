package java_swing.ex10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class DodgeGame extends JFrame {
    private GamePanel gamePanel;

    public DodgeGame() {
        setTitle("Dodge Game");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new DodgeGame();
    }
}

class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private int playerX = 200;
    private int playerY = 500;
    private int playerWidth = 50;
    private int playerHeight = 50;
    private int playerSpeed = 10;

    private ArrayList<Rectangle> obstacles;
    private int obstacleWidth = 50;
    private int obstacleHeight = 50;
    private int obstacleSpeed = 5;
    private int score = 0;
    private Random random;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        obstacles = new ArrayList<>();
        random = new Random();

        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 플레이어 그리기
        g.setColor(Color.BLUE);
        g.fillRect(playerX, playerY, playerWidth, playerHeight);

        // 장애물 그리기
        g.setColor(Color.RED);
        for (Rectangle obstacle : obstacles) {
            g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }

        // 점수 표시
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 장애물 이동 및 화면 밖 장애물 제거
        for (int i = 0; i < obstacles.size(); i++) {
            Rectangle obstacle = obstacles.get(i);
            obstacle.y += obstacleSpeed;
            if (obstacle.y > getHeight()) {
                obstacles.remove(i);
                score++;
            }
        }

        // 새로운 장애물 생성
        if (random.nextInt(20) == 0) {
            int x = random.nextInt(getWidth() - obstacleWidth);
            obstacles.add(new Rectangle(x, 0, obstacleWidth, obstacleHeight));
        }

        // 충돌 체크
        for (Rectangle obstacle : obstacles) {
            if (obstacle.intersects(new Rectangle(playerX, playerY, playerWidth, playerHeight))) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
                System.exit(0);
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && playerX > 0) {
            playerX -= playerSpeed;
        } else if (key == KeyEvent.VK_RIGHT && playerX < getWidth() - playerWidth) {
            playerX += playerSpeed;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 키 릴리스 시 추가 동작 없음
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 키 타이핑 시 추가 동작 없음
    }
}

package java_swing.TestProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class BulletDodgeGame extends JFrame {
    private GamePanel gamePanel;

    public BulletDodgeGame() {
        setTitle("총알 피하기 게임");
        setSize(800, 600); // 창 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 창을 화면 중앙에 위치
        gamePanel = new GamePanel();
        add(gamePanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BulletDodgeGame();
    }
}

class GamePanel extends JPanel implements KeyListener, ActionListener {
    private int wallThickness = 20;
    private int playerSize = 30;
    private int playerX, playerY;
    private int moveSpeed = 10;
    private boolean isGameOver = false;

    private Timer timer; // 타이머를 이용해 게임 시간 측정
    private long startTime;
    private ArrayList<Bullet> bullets = new ArrayList<>(); // 총알 목록
    private Random random = new Random();

    public GamePanel() {
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
        
        // 플레이어 초기 위치 설정
        playerX = getWidth() / 2 - playerSize / 2;
        playerY = getHeight() / 2 - playerSize / 2;

        // 타이머 설정
        timer = new Timer(20, this);
        timer.start();
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 벽 그리기
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), wallThickness);
        g.fillRect(0, getHeight() - wallThickness, getWidth(), wallThickness);
        g.fillRect(0, 0, wallThickness, getHeight());
        g.fillRect(getWidth() - wallThickness, 0, wallThickness, getHeight());

        // 플레이어 그리기
        if (!isGameOver) {
            g.setColor(Color.BLUE);
            g.fillRect(playerX, playerY, playerSize, playerSize);
        }

        // 총알 그리기
        g.setColor(Color.RED);
        for (Bullet bullet : bullets) {
            g.fillOval(bullet.x, bullet.y, bullet.size, bullet.size);
        }

        // 게임 오버 시 메세지 출력
        if (isGameOver) {
            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", getWidth() / 2 - 100, getHeight() / 2);
            g.drawString("Survived Time: " + elapsedTime + " seconds", getWidth() / 2 - 150, getHeight() / 2 + 50);
        }
    }

    // 키보드 입력 처리
    @Override
    public void keyPressed(KeyEvent e) {
        if (isGameOver) return;

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) movePlayer(-moveSpeed, 0);
        if (key == KeyEvent.VK_RIGHT) movePlayer(moveSpeed, 0);
        if (key == KeyEvent.VK_UP) movePlayer(0, -moveSpeed);
        if (key == KeyEvent.VK_DOWN) movePlayer(0, moveSpeed);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private void movePlayer(int dx, int dy) {
        playerX += dx;
        playerY += dy;

        // 벽 충돌 감지
        if (playerX < wallThickness) playerX = wallThickness;
        if (playerY < wallThickness) playerY = wallThickness;
        if (playerX > getWidth() - wallThickness - playerSize) 
            playerX = getWidth() - wallThickness - playerSize;
        if (playerY > getHeight() - wallThickness - playerSize) 
            playerY = getHeight() - wallThickness - playerSize;

        repaint();
    }

    // 총알 추가 메서드
    private void addBullet() {
        int size = 10; // 총알 크기
        int speed = 5; // 총알 속도
        int direction = random.nextInt(4); // 4개의 방향 중 하나를 무작위로 선택
        int x = 0, y = 0;

        // 각 방향에 따라 총알 위치 초기화
        if (direction == 0) { // 상단
            x = random.nextInt(getWidth());
            y = 0;
        } else if (direction == 1) { // 하단
            x = random.nextInt(getWidth());
            y = getHeight() - size;
        } else if (direction == 2) { // 좌측
            x = 0;
            y = random.nextInt(getHeight());
        } else if (direction == 3) { // 우측
            x = getWidth() - size;
            y = random.nextInt(getHeight());
        }

        bullets.add(new Bullet(x, y, size, speed, direction));
    }

    // 타이머 동작
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isGameOver) {
            timer.stop();
            return;
        }

        // 총알 생성 주기 조절
        if (random.nextInt(20) == 0) { // 무작위로 총알 생성 빈도 결정
            addBullet();
        }

        // 총알 이동 및 충돌 감지
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.move();

            // 플레이어와 총알 충돌 감지
            if (new Rectangle(playerX, playerY, playerSize, playerSize).intersects(
                    new Rectangle(bullet.x, bullet.y, bullet.size, bullet.size))) {
                isGameOver = true;
                repaint();
                return;
            }
        }
        repaint();
    }
}
//총알 클래스
class Bullet {
 int x, y, size, speed, direction;

 public Bullet(int x, int y, int size, int speed, int direction) {
     this.x = x;
     this.y = y;
     this.size = size;
     this.speed = speed;
     this.direction = direction;
 }

 public void move() {
     // 방향에 따라 총알 이동
     switch (direction) {
         case 0: y += speed; break; // 아래쪽
         case 1: y -= speed; break; // 위쪽
         case 2: x += speed; break; // 오른쪽
         case 3: x -= speed; break; // 왼쪽
     }
 }
}



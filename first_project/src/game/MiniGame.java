package game;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.text.*;
import java.util.*;

/**
 * 1~25 숫자 차례대로 클릭하기
 * @author 송지은
 *
 */
public class MiniGame extends JFrame implements MouseListener, Runnable {

   private JLabel title = new JLabel();
   private JLabel time = new JLabel();
   private JButton start = new JButton("START");
   private JButton reset = new JButton("RESET");
   private JLabel label = new JLabel();
   SimpleDateFormat format;
   String show_time;
   long start_time;
   long current_time;
   long actual_time;
   boolean time_run = false;
   
   Thread th;
   ImagePanel ip;
   
   MiniGame() {
      init();
      start();
      setTitle("JJuruButton");
      setSize(600, 600);
      Dimension ipreen = Toolkit.getDefaultToolkit().getScreenSize();
      int xpos = (int) ((ipreen.getWidth()-getWidth()) / 2);
      int ypos = (int) ((ipreen.getHeight()- getHeight()) / 2);
      setLocation(xpos, ypos);
      setResizable(false);
      setVisible(true);
   }


   public static void startGame(){
	   new MiniGame();
   }
   
   public void init() {
      Container con = this.getContentPane();
      con.setLayout(null);
      
      title.setBounds(200, 10, 300, 30);
      title.setFont(new Font("Default", Font.BOLD, 20));
      con.add(title);

      time.setBounds(400, 50, 150, 30);
      time.setFont(new Font("Default", Font.BOLD, 20));
      con.add(time);
      
      start.setBounds(100, 520, 100, 30);
      start.setFont(new Font("Default", Font.BOLD, 20));
      con.add(start);

      reset.setBounds(400, 520, 100, 30);
      reset.setFont(new Font("Default", Font.BOLD, 20));
      con.add(reset);

      ip = new ImagePanel();
      ip.setBounds(100, 100, 410, 410);
      con.add(ip);
   }


   public void start() {
      this.addMouseListener(this);
      start.addMouseListener(this);
      reset.addMouseListener(this);
      th = new Thread(this);
      th.start();
      
      format = new SimpleDateFormat("HH:mm:ss.SSS");
      time.setText("00:00:00.000");
      title.setText("JJuruButton 1 to 25");
      ip.gameStart(false);
   }

	public void run() {
		while (true) {
			repaint();
			TimeCheck();

			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

   public void TimeCheck() {

      if (time_run) {
         current_time = System.currentTimeMillis();
         actual_time = current_time - start_time;
         ip.countDown((int) actual_time / 1000);
         
         if (!ip.game_start && ip.check <= 25) {
            show_time = format.format(actual_time - 32403000);
            time.setText(show_time);
         }
      }

      if (ip.check > 24) {
         ip.ClearTime(time.getText());
      }
   }

   public void mouseClicked(MouseEvent e) {
      if (e.getSource() == start) {
         if (!time_run && !ip.game_start) {
            start_time = System.currentTimeMillis();
            ip.rect_select.clear();
            time_run = true;
            ip.gameStart(true);
         }

      } else if (e.getSource() == reset) {
         start_time = 0;
         time.setText("00:00:00.000");
         ip.rect_select.clear();
         ip.countDown(0);
         time_run = false;
         ip.gameStart(false);
         ip.check = 0;

      }

   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

}

class ImagePanel extends JPanel implements MouseListener {
   
   int count = 3;
   int x, y;
   int check;
   String time;
   boolean game_start = false;

   Random ran_num = new Random();
   Vector rect_select = new Vector();
   SelectRect sr;
   ImagePanel() {
      this.addMouseListener(this);
   }

   public void countDown(int count) {
      switch (count) {

      case 0:
         this.count = 3;
         break;

      case 1:
         this.count = 2;
         break;

      case 2:
         this.count = 1;
         break;

      case 3:
         this.count = 0;
         game_start = false;
         break;
      }
   }

   public void gameStart(boolean game_start) {
      this.game_start = game_start;

      if (this.game_start) {
         check = 1;
         for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
               int num = 0;
               int xx = 5 + i * 80;
               int yy = 5 + j * 80;
               num = ran_num.nextInt(25)+1;

               for (int k = 0; k < rect_select.size(); ++k) {
                  sr = (SelectRect) rect_select.get(k);
                  if (sr.number == num) {
                     num = ran_num.nextInt(25)+1;
                     k = -1;
                  }
               }
               sr = new SelectRect(xx, yy, num);
               rect_select.add(sr);
            }

         }

      }

   }

   public void paint(Graphics g) {
      g.drawRect(0, 0, 400, 400);
      
      if (game_start) {
         g.setFont(new Font("Default", Font.BOLD, 50));
         g.drawString("CountDown", 70, 150);
         g.setFont(new Font("Default", Font.BOLD, 100));
         g.drawString("" + count, 170, 250);
      
      } else if (!game_start && count == 0) {
         
         for (int i = 0; i < rect_select.size(); ++i) {
            sr = (SelectRect) rect_select.get(i);
            g.drawRect(sr.x, sr.y, 70, 70);
            g.setFont(new Font("Default", Font.BOLD, 30));
            g.drawString("" + sr.number, sr.x + 22, sr.y + 45);
         }
         g.setColor(Color.red);
         g.drawRect(x * 80 + 5, y * 80 + 5, 70, 70);

      }

      if (check > 25) {
         g.setColor(Color.blue);
         g.setFont(new Font("Default", Font.BOLD, 50));
         g.drawString("GAME CLEAR!", 40, 150);
         g.setColor(Color.red);
         g.setFont(new Font("Default", Font.BOLD, 40));
         g.drawString("" + time, 90, 250);

      }

   }

   public void ClearTime(String time) {
      this.time = time;
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {

      x = e.getX() / 80;
      y = e.getY() / 80;
      if (count == 0) {
         for (int i = 0; i < rect_select.size(); ++i) {
            sr = (SelectRect) rect_select.get(i);
            if (x == sr.x / 80 && y == sr.y / 80) {
               int xx = sr.x;
               int yy = sr.y;
               if (sr.number - check == 0) {
                  check++;
                  rect_select.remove(i);  
                
               }
         

            }

         }

      }

   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }
}

class SelectRect {
   int x, y;
   int number;

   SelectRect(int x, int y, int number) {
   this.x = x;
   this.y = y;
   this.number = number;

   }
}
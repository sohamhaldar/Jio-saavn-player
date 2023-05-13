package com.player;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Scanner;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

import static com.player.current;
import java.awt.*;
import java.awt.TrayIcon.MessageType;

class name extends Thread {
    private String mssg;
    public void run()
    {
        while (player.start == true) {
            WebElement namesong=player.driver.findElement(By.xpath("/html/body/div[1]/div[2]/aside[3]/div[1]/figure/figcaption/h4"));
            if(current.equals(namesong.getText())){
            }
            else{
                System.out.println("playing:"+namesong.getText());
                System.out.println("press 'p'(play/pause) ,'a'(previous),'d'(next),'s'(stop)");
                current=namesong.getText();
                if (SystemTray.isSupported()) {
                    TrayIcondemo td = new TrayIcondemo();
                    try {
                        td.displayTray();
                    } catch (AWTException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.err.println("System tray not supported!");
                }
            }
            try {
                Thread.sleep(1000);
            }
            catch (Exception err) {
            }
        }
    }
}
class TrayIcondemo {

    public void displayTray() throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);
        trayIcon.displayMessage(current,"now playing", MessageType.INFO);
    }
}


public class player {
    static String current;
    public static WebDriver driver;
    public static boolean start = true;

    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        System.setProperty("webdriver.chrome.driver","C:\\browserdriver\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--window-size=1920,1200");
        chromeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors","--disable-extensions","--no-sandbox","--disable-dev-shm-usage");
    
        String url="https://jiosaavn.com/search/song/";
        System.out.println("enter song name:");
        String we=sc.nextLine();
        for(int i=0;i<we.length();i++){
            if(we.charAt(i)!=' '){
                url=url+we.charAt(i);
            } else if (we.charAt(i)==' ') {
                url=url+"%20";
            }
        }
        driver=new ChromeDriver(chromeOptions);
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        int i=1;
        WebElement pause;
        for(i=1;i<5;i++){
            try {
                pause = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/main/div/div/section/ol/li[" + i + "]/div/article/div[2]/figure/figcaption"));
                System.out.println(i + ":" + pause.getText());
            }
            finally {
                continue;
            }
        }
        System.out.println("enter your choice");
        int result=sc.nextInt();
        WebElement song=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/main/div/div/section/ol/li["+result+"]/div/article/div[2]/figure/div"));
        JavascriptExecutor js=(JavascriptExecutor) driver;
        if(result>=2&&result<7){
            js.executeScript("window.scrollTo(0,200)");}
        else if(result>=7){
            js.executeScript("window.scrollTo(0,280)");}

        song.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        pause= driver.findElement(By.id("player_play_pause"));
        WebElement prev=driver.findElement(By.id("player_prev"));
        WebElement next= driver.findElement(By.id("player_next"));

        name newThread = new name();
        WebElement namesong=driver.findElement(By.xpath("/html/body/div[1]/div[2]/aside[3]/div[1]/figure/figcaption/h4"));
        String a=namesong.getText();
        current=a;
        System.out.println("playing now:"+current);
        newThread.start();
        while (true){
            System.out.println("press 'p'(play/pause) ,'a'(previous),'d'(next),'s'(stop)");
            char ans=sc.next().charAt(0);
            if(ans=='p'){
                pause.click();
            }
            else if (ans=='a') {
                prev.click();
            }
            else if (ans=='d') {
                next.click();
            }
            else if(ans=='s'){
                System.out.println("exiting...");
                driver.quit();
                newThread.interrupt();
                System.exit(0);
            }

        }
    }

}

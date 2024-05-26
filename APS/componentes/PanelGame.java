package componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import objetos.Atingir;
import objetos.Efeitos;
import objetos.Jogador;
import objetos.Inimigos;

public class PanelGame extends JComponent {

    private Graphics2D g2;
    private BufferedImage image;
    private int width;
    private int height;
    private Thread thread;
    private boolean start = true;
    private Teclado teclado;
    private int balaTime;

    //FPS
    private final int FPS = 60;
    private final int TEMPO_FINAl = 1000000000 / FPS;

    //Objetos
    private Jogador jogador;
    private List<Atingir> balas;
    private List<Inimigos> inimigos;
    private List<Efeitos> efeitos;
    private int pontos = 0;

    public void start(){
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        thread = new Thread(new Runnable() {
           @Override
           public void run(){
            while(start){
                long startTime = System.nanoTime();
                Background();
                drawJogo();
                render();
                long time = System.nanoTime() - startTime;
                if(time < TEMPO_FINAl){
                    long sleep = (TEMPO_FINAl - time) / 1000000;
                    sleep(sleep);
                }
             }
           } 
        });
        initObj();
        initTeclado();
        initBalas();
        thread.start();
        
    }

    private void addInimgos(){
        Random random = new Random();
        int locY = random.nextInt(height - 50)+ 25;
        Inimigos inimigo = new Inimigos();
        inimigo.mover(0, locY);
        inimigo.mudarAngulo(0);
        inimigos.add(inimigo);
        int loY2 = random.nextInt(height - 50)+ 25;
        Inimigos inimigo2 = new Inimigos();
        inimigo2.mover(width, loY2);
        inimigo2.mudarAngulo(180);
        inimigos.add(inimigo2);
    }

    private void initObj(){
        jogador = new Jogador();
        jogador.mover(150,150);
        inimigos = new ArrayList<>();
        efeitos = new ArrayList<>();
        new Thread(new Runnable(){
            @Override
            public void run(){
                while (start) {
                    addInimgos();
                    sleep(3000);
                }
            }
        }).start();
    }

    private void resetJogo(){
        pontos = 0;
        inimigos.clear();
        balas.clear();
        jogador.mover(150, 150);
        jogador.reset();
    }

    public void initTeclado(){
        teclado = new Teclado(); 
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_A){
                    teclado.setEsquerda(true);
                } 
                else if(e.getKeyCode() == KeyEvent.VK_D){
                    teclado.setDireita(true);
                }
                else if(e.getKeyCode() == KeyEvent.VK_W){
                    teclado.setTecla_W(true);
                }
                else if(e.getKeyCode() == KeyEvent.VK_J){
                    teclado.setTecla_J(true);
                }
                else if(e.getKeyCode() == KeyEvent.VK_K){
                    teclado.setTecla_K(true);
                }
                else if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    teclado.setTecla_Enter(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_A){
                    teclado.setEsquerda(false);
                } 
                else if(e.getKeyCode() == KeyEvent.VK_D){
                    teclado.setDireita(false);
                }
                else if(e.getKeyCode() == KeyEvent.VK_W){
                    teclado.setTecla_W(false);
                }
                else if(e.getKeyCode() == KeyEvent.VK_J){
                    teclado.setTecla_J(false);
                }
                else if(e.getKeyCode() == KeyEvent.VK_K){
                    teclado.setTecla_K(false);
                }
                else if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    teclado.setTecla_Enter(false);
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run(){
                float s = 0.5f;
                while (start) {
                    if(jogador.isAlive()){
                        float angulo = jogador.getAngulo();
                        if(teclado.isEsquerda()){
                            angulo -= s;
                        }
                        if(teclado.isDireita()){
                            angulo += s;
                        }
                        if(teclado.isTecla_J() || teclado.isTecla_K()){
                            if(balaTime == 0){
                                if(teclado.isTecla_J()){
                                    balas.add(0, new Atingir(jogador.getX(), jogador.getY(), jogador.getAngulo(), 5, 3f));
                                }
                                else{
                                    balas.add(0, new Atingir(jogador.getX(), jogador.getY(), jogador.getAngulo(), 20, 3f));
                                }
                            }
                            balaTime++;
                            if(balaTime == 15){
                                balaTime = 0;
                            }
                        } else{
                            balaTime = 0;
                        }
                        if(teclado.isTecla_W()){
                            jogador.velocidadeON();
                        }else{
                            jogador.devagar();
                        }
                        jogador.atualizar();
                        jogador.mudarAngulo(angulo); 
                    }
                    else{
                       if(teclado.isTecla_Enter()){
                        resetJogo();
                       } 
                    }
                    for(int i = 0; i < inimigos.size(); i++){
                        Inimigos inimigo = inimigos.get(i);
                        if(inimigo != null){
                            inimigo.atualizar();
                            if(!inimigo.check(width, height)){
                                inimigos.remove(inimigo);
                            }
                            else{
                                if(jogador.isAlive()){
                                    checkJogador(inimigo);
                                }
                            }
                        }
                    }
                    sleep(5);
                }
            }
        }).start();
    }

    private void initBalas(){
        balas = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run(){
                while (start) {
                    for(int i = 0; i < balas.size(); i++){
                        Atingir bala = balas.get(i);
                        if(balas != null){
                            bala.atualizar();
                            checkBalas(bala);
                            if(!bala.check(width, height)){
                                balas.remove(bala);
                            }
                        }
                        else{
                            balas.remove(bala);
                        }
                    }
                    for(int i = 0; i < efeitos.size(); i++){
                        Efeitos efeito = efeitos.get(i);
                        if(efeito != null){
                            efeito.atualizar();
                            if(!efeito.check()){
                                efeitos.remove(efeito);
                            }
                        }
                        else{
                            efeitos.remove(efeito);
                        }
                    }
                    sleep(1);
                }
            }
        }).start();
    }

    private void checkBalas(Atingir bala){
        for(int i =0; i < inimigos.size(); i++){
            Inimigos inimigo = inimigos.get(i);
            if(inimigo != null){
                Area area = new Area(bala.getForma());
                area.intersect(inimigo.getForma());
                if(!area.isEmpty()){
                    efeitos.add(new Efeitos(bala.getCentroX(), bala.getCentroY(), 3, 5, 60, 0.5f, new Color(230, 207, 105)));
                    if(!inimigo.atualizarHP(bala.getTamanho())){
                        pontos++;
                        inimigos.remove(inimigo);
                        double x = inimigo.getX() + inimigo.getTamanhoI() / 2;
                        double y = inimigo.getY() + inimigo.getTamanhoI() / 2;
                        efeitos.add(new Efeitos(x, y, 5, 5, 75, 0.05f, new Color(32, 178, 169)));
                        efeitos.add(new Efeitos(x, y, 5, 5, 75, 0.1f, new Color(32, 178, 169)));
                        efeitos.add(new Efeitos(x, y, 10, 10, 100, 0.3f, new Color(230, 207, 105)));
                        efeitos.add(new Efeitos(x, y, 10, 5, 100, 0.5f, new Color(255, 70, 70)));
                        efeitos.add(new Efeitos(x, y, 10, 5, 150, 0.2f, new Color(255, 255, 255)));
                    }
                    //inimigos.remove(inimigo);
                    balas.remove(bala);
                }
            }
        }
    }

    private void checkJogador(Inimigos inimigo){
        if (inimigo != null){
            Area area = new Area(jogador.getForma());
            area.intersect(inimigo.getForma());
            if(!area.isEmpty()){
                double inimigoHP = inimigo.getHP();
                if(!inimigo.atualizarHP(jogador.getHP())){
                    inimigos.remove(inimigo);
                    double x = inimigo.getX() + inimigo.getTamanhoI() / 2;
                        double y = inimigo.getY() + inimigo.getTamanhoI() / 2;
                        efeitos.add(new Efeitos(x, y, 5, 5, 75, 0.05f, new Color(32, 178, 169)));
                        efeitos.add(new Efeitos(x, y, 5, 5, 75, 0.1f, new Color(32, 178, 169)));
                        efeitos.add(new Efeitos(x, y, 10, 10, 100, 0.3f, new Color(230, 207, 105)));
                        efeitos.add(new Efeitos(x, y, 10, 5, 100, 0.5f, new Color(255, 70, 70)));
                        efeitos.add(new Efeitos(x, y, 10, 5, 150, 0.2f, new Color(255, 255, 255)));
                }
                if(!jogador.atualizarHP(inimigoHP)){
                    jogador.setAlive(false);
                    double x = inimigo.getX() + inimigo.getTamanhoI() / 2;
                        double y = inimigo.getY() + inimigo.getTamanhoI() / 2;
                        efeitos.add(new Efeitos(x, y, 5, 5, 75, 0.05f, new Color(32, 178, 169)));
                        efeitos.add(new Efeitos(x, y, 5, 5, 75, 0.1f, new Color(32, 178, 169)));
                        efeitos.add(new Efeitos(x, y, 10, 10, 100, 0.3f, new Color(230, 207, 105)));
                        efeitos.add(new Efeitos(x, y, 10, 5, 100, 0.5f, new Color(255, 70, 70)));
                        efeitos.add(new Efeitos(x, y, 10, 5, 150, 0.2f, new Color(255, 255, 255)));
                }
            }
        }
    }

    private void Background(){
        g2.setColor(new Color(0,0,25));
        g2.fillRect(0,0, width, height);
    }

    private void drawJogo(){
        if(jogador.isAlive()){
            jogador.draw(g2);
        }
        for(int i=0; i<balas.size();i++){
            Atingir bala = balas.get(i);
            if(bala != null){
                bala.draw(g2);
            }
        }

        for(int i = 0; i < inimigos.size(); i++){
            Inimigos inimigo = inimigos.get(i);
            if(inimigo != null){
                inimigo.draw(g2);
            }
        }
        
        for(int i = 0; i < efeitos.size(); i++){
            Efeitos efeito = efeitos.get(i);
            if(efeito != null){
                efeito.draw(g2);
            }
        }
        g2.setColor(Color.WHITE);
        g2.setFont(getFont().deriveFont(Font.BOLD, 20f));
        g2.drawString("Pontos: "+ pontos, 10, 25);

        if(!jogador.isAlive()){
            String texto = "GAME OVER";
            String texto_tecla = "Pressione ENTER para Continuar";
            g2.setFont(getFont().deriveFont(Font.BOLD, 55f));
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r2 = fm.getStringBounds(texto, g2);
            double textoWidth = r2.getWidth();
            double textoHeight = r2.getHeight();
            double x = (width - textoWidth) / 2;
            double y = (height - textoHeight) / 2;
            g2.drawString(texto, (int) x, (int) y + fm.getAscent());
            g2.setFont(getFont().deriveFont(Font.BOLD, 20f));
            fm = g2.getFontMetrics();
            r2 = fm.getStringBounds(texto_tecla, g2);
            textoWidth = r2.getWidth();
            textoHeight = r2.getHeight();
            x = (width - textoWidth) / 2;
            y = (height - textoHeight) / 2;
            g2.drawString(texto_tecla, (int) x, (int) y + fm.getAscent() + 50);
        }
    }

    private void render(){
        Graphics g = getGraphics();
        g.drawImage(image, 0,0, null);
        g.dispose();
    }

    private void sleep(long speed){
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
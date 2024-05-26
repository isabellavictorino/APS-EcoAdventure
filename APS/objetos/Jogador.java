package objetos;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import javax.swing.ImageIcon;

public class Jogador extends HpRender{
    
    public Jogador(){
        super(new HP(50,50));
        this.image = new ImageIcon(getClass().getResource("/imagens/barco.png")).getImage();
        this.image_speed = new ImageIcon(getClass().getResource("/imagens/barco.png")).getImage();;
        Path2D p = new Path2D.Double();
        p.moveTo(0, 15);
        p.lineTo(20, 5);
        p.lineTo(TAMANHO_J + 15, TAMANHO_J / 2);
        p.lineTo(20, TAMANHO_J - 5);
        p.lineTo(0, TAMANHO_J - 15);
        formaJogador = new Area(p);
    }

    public static final double TAMANHO_J = 64;
    private double x;
    private double y;
    private final float MAX_VELOCIDADE = 1f;
    private float velocidade = 0f;
    private float angulo = 0f;
    private final Area formaJogador;
    private final Image image;
    private final Image image_speed;
    private boolean velocidadeON;
    private boolean vivo = true;

    public void mover(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void atualizar(){
        x += Math.cos(Math.toRadians(angulo)) * velocidade;
        y += Math.sin(Math.toRadians(angulo)) * velocidade;
    }

    public void mudarAngulo(float angulo){
        if(angulo < 0){
            angulo = 359;
        }
        else if(angulo > 359){
            angulo = 0;
        }
        this.angulo = angulo;
    }

    public void draw(Graphics2D g2){
        AffineTransform transforme = g2.getTransform();
        g2.translate(x,y);
        AffineTransform tr = new AffineTransform();
        tr.rotate(Math.toRadians(angulo+45), TAMANHO_J / 2, TAMANHO_J / 2);
        g2.drawImage(velocidadeON ? image_speed : image, tr, null);
        hpRender(g2, getForma(), y);
        g2.setTransform(transforme);

        //g2.setColor(new Color(12,173,84));
        //g2.draw(getForma());
        //g2.draw(getForma().getBounds());
    }

    public Area getForma(){
        AffineTransform afx = new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angulo), TAMANHO_J / 2, TAMANHO_J / 2);
        return new Area(afx.createTransformedShape(formaJogador));
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public float getAngulo(){
        return angulo;
    }

    public void velocidadeON(){
        velocidadeON = true;
        if(velocidade > MAX_VELOCIDADE){
            velocidade = MAX_VELOCIDADE;
        }
        else{
            velocidade += 0.01f;
        }
    }

    public void devagar(){
        velocidadeON = false;
        if(velocidade <= 0 ){
            velocidade = 0;
        }
        else{
            velocidade -= 0.003f;
        }
    }

    public boolean isAlive(){
        return vivo;
    }

    public void setAlive(boolean vivo){
        this.vivo = vivo;
    }

    public void reset(){
        vivo = true;
        resetHP();
        angulo = 0;
        velocidade = 0;
    }
}
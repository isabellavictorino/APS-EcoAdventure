package objetos;

//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.Random;
import javax.swing.ImageIcon;

public class Inimigos extends HpRender {

    public Inimigos(){
        super(new HP(20,20));

        Random random = new Random();
        int num = random.nextInt(300);
        if(num >= 0 && num <= 100){
            this.image = new ImageIcon(getClass().getResource("/imagens/garrafa.png")).getImage();
        }
        else if(num > 100 && num <= 200){
            this.image = new ImageIcon(getClass().getResource("/imagens/latinha.png")).getImage();
        }
        else{
            this.image = new ImageIcon(getClass().getResource("/imagens/banana.png")).getImage();
        }
        Path2D p = new Path2D.Double();
        p.moveTo(0, TAMANHO_I / 2);
        p.lineTo(15, 10);
        p.lineTo(TAMANHO_I-5, 13);
        p.lineTo(TAMANHO_I + 10, TAMANHO_I / 2);
        p.lineTo(TAMANHO_I - 5, TAMANHO_I - 13);
        p.lineTo(15, TAMANHO_I - 10);
        inimigoArea = new Area(p);
    }

    public static final double TAMANHO_I = 50;
    private double x;
    private double y;
    private final float velocidade = 0.3f;
    private float angulo = 0;
    private final Image image;
    private final Area inimigoArea;

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
        g2.translate(x, y);
        AffineTransform tr = new AffineTransform();
        tr.rotate(Math.toRadians(angulo + 45), TAMANHO_I / 2, TAMANHO_I /2);
        g2.drawImage(image, tr, null);
        Shape forma = getForma();
        hpRender(g2, forma, y);
        g2.setTransform(transforme);
        //g2.setColor(new Color(36, 214, 63));
        //g2.draw(forma);
        //g2.draw(forma.getBounds2D());
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

    public double getTamanhoI(){
        return TAMANHO_I;
    }

    public Area getForma(){
        AffineTransform afx = new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angulo), TAMANHO_I / 2, TAMANHO_I / 2);
        return new Area(afx.createTransformedShape(inimigoArea));
    }

    public boolean check(int width, int height){
        Rectangle tamanho = getForma().getBounds();
        if(x <= -tamanho.getWidth() || y < -tamanho.getHeight() || x > width || y > height){
            return false;
        }
        else{
            return true;
        }
    }
}

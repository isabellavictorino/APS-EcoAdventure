package objetos;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Efeitos {
    private final double x;
    private final double y;
    private final double max_distancia;
    private final int max_tamanho;
    private final int efeitoTotal;
    private final float velocidade;
    private final Color cor;
    private double distancia_atual;
    private Boom booms[];
    private float alpha = 1f;

    public Efeitos(double x, double y, int efeitoTotal, int max_tamanho, double max_distancia, float velocidade, Color cor ){
        this.x = x;
        this.y = y;
        this.efeitoTotal = efeitoTotal;
        this.max_tamanho = max_tamanho;
        this.max_distancia = max_distancia;
        this.velocidade = velocidade;
        this.cor = cor;
        criaAleatorio();
    }

    private void criaAleatorio(){
        booms = new Boom[efeitoTotal];
        float per = 360f / efeitoTotal;
        Random random = new Random();
        for(int i = 1; i <= efeitoTotal; i++){
            int r = random.nextInt((int)per) + 1;
            int boom_tamanho = random.nextInt(max_tamanho) + 1;
            float angulo = i * per + r;
            booms[i - 1] = new Boom(boom_tamanho, angulo);
        }
    }

    public void draw(Graphics2D g2){
       AffineTransform transform = g2.getTransform();
       Composite composite = g2.getComposite();
        g2.setColor(cor);
        g2.translate(x, y);
        for(Boom b:booms){
            double bx = Math.cos(Math.toRadians(b.getAngulo())) * distancia_atual;
            double by = Math.sin(Math.toRadians(b.getAngulo())) * distancia_atual;
            double boom_tamanho = b.getTamanho();
            double espaco = boom_tamanho / 2;
            if(distancia_atual >= max_distancia - (max_distancia * 0.7f)){
                alpha = (float) ((max_distancia - distancia_atual) / (max_distancia * 0.7f));
            }
            if(alpha > 1){
                alpha = 1;
            }
            else if (alpha < 0){
                alpha = 0;
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
            g2.fill(new Rectangle2D.Double(bx - espaco, by - espaco, boom_tamanho, boom_tamanho));
        }

       g2.setComposite(composite);
       g2.setTransform(transform);
    }

    public void atualizar(){
        distancia_atual += velocidade;
    }
    
    public boolean check(){
        return distancia_atual < max_distancia;
    }
}
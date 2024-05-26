package objetos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class HpRender {
    private final HP hp;

    public HpRender(HP hp){
        this.hp = hp;
    }

    protected void hpRender(Graphics2D g2, Shape tamanho, double y){
        if(hp.getHP_Atual() != hp.getMAX_HP()){
            double hpY = tamanho.getBounds().getY() - y - 10;
            g2.setColor(new Color(70,70,70));
            g2.fill(new Rectangle2D.Double(0, hpY, Jogador.TAMANHO_J, 2));
            g2.setColor(new Color(253, 91,91));
            double hpTamanho = hp.getHP_Atual() / hp.getMAX_HP() * Jogador.TAMANHO_J;
            g2.fill(new Rectangle2D.Double(0, hpY, hpTamanho, 2));
    
        }
    }
    
    public boolean atualizarHP(double cHP){
        hp.setHP_Atual(hp.getHP_Atual() -cHP);
        return hp.getHP_Atual() > 0;
    }

    public double getHP(){
        return hp.getHP_Atual();
    }

    public void resetHP(){
        hp.setHP_Atual(hp.getMAX_HP());
    }
    
}
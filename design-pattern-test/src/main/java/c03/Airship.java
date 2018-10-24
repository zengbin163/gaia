package c03;

import c03.pojo.Engine;
import c03.pojo.EscapeTower;
import c03.pojo.OrbitalModule;

/**
 * Created by 张少昆 on 2018/4/17.
 */
public class Airship {
   private Engine engine;
   private EscapeTower escapeTower;
   private OrbitalModule orbitalModule;

    public Engine getEngine(){
        return engine;
    }

    public void setEngine(Engine engine){
        this.engine = engine;
    }

    public EscapeTower getEscapeTower(){
        return escapeTower;
    }

    public void setEscapeTower(EscapeTower escapeTower){
        this.escapeTower = escapeTower;
    }

    public OrbitalModule getOrbitalModule(){
        return orbitalModule;
    }

    public void setOrbitalModule(OrbitalModule orbitalModule){
        this.orbitalModule = orbitalModule;
    }
}

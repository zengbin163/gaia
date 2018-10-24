package c03;

/**
 * Created by 张少昆 on 2018/4/17.
 */
public class AirshipDirector {
    private EngineBuilder engineBuilder;
    private EscapeTowerBuilder escapeTowerBuilder;
    private OrbitalModuleBuilder orbitalModuleBuilder;

    public AirshipDirector(EngineBuilder engineBuilder, EscapeTowerBuilder escapeTowerBuilder, OrbitalModuleBuilder orbitalModuleBuilder){
        this.engineBuilder = engineBuilder;
        this.escapeTowerBuilder = escapeTowerBuilder;
        this.orbitalModuleBuilder = orbitalModuleBuilder;
    }

    public Airship create(){
        Airship airship = new Airship();
        airship.setEngine(engineBuilder.create());
        airship.setEscapeTower(escapeTowerBuilder.create());
        airship.setOrbitalModule(orbitalModuleBuilder.create());

        return airship;
    }
}

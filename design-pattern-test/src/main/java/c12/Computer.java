package c12;

/**
 * Created by zengbin on 2018/4/18.
 */
public abstract class Computer {
    protected Brand brand;

    public Computer(Brand brand){
        this.brand = brand;
    }

    public void sale(){
        brand.sale();
    }
}

class Desktop extends Computer {

    public Desktop(Brand brand){
        super(brand);
        System.out.println("sell Desktop");
    }
}

class Laptop extends Computer {

    public Laptop(Brand brand){
        super(brand);
        System.out.println("sell Laptop");
    }
}

class Pad extends Computer {

    public Pad(Brand brand){
        super(brand);
        System.out.println("sell Pad");
    }
}

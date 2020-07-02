package Model.Interface;


import Exception.*;
import Model.Data.Data;
import Model.Data.Data;

public interface Packable <T extends Packable<T>>{

    Data<T> pack();

    T dpkg(Data<T> data) ;

    long getId();


}

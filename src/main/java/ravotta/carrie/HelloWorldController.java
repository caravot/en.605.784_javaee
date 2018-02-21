package ravotta.carrie;//package edu.jhu.jee.felikson.leonid;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@Named(value = "helloWorldController")
@RequestScoped
@ManagedBean
public class HelloWorldController implements Serializable {
    private String hello;

    public HelloWorldController() {
        System.out.println("Instantiated helloWorldController");
        hello = "Hello World!";
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}
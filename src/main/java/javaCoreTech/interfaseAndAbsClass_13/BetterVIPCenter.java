package javaCoreTech.interfaseAndAbsClass_13;

public class BetterVIPCenter {

        private Map<User.TYPE, ServiceProvider> providers;
        void serviceVIP(T extend User user） {
            providers.get(user.getType()).service(user);
        }
    interface ServiceProvider{
        void service(T extend User user) ;
    }
    class SlumDogVIPServiceProvider implements ServiceProvider{
        void service(T extend User user){
            // do somthing
        }
    }
    class RealVIPServiceProvider implements ServiceProvider{
        void service(T extend User user) {
            // do something
        }
    }


}

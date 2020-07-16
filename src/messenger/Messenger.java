
package messenger;


public class Messenger {
    
    public static Conector servidor,cliente;
    public static void main (String[] args){
          VCliente cliente = new VCliente();
          cliente.show();
        //Login login = new Login();
        //login.show();
    }
    
    public static Conector initCliente(String ip)
    {
        try
        {    
            cliente = new Conector(ip);
            cliente.start();
        }catch(Exception ex){
            System.out.println("Ocurrio un error al iniciar cliente: "+ ex);
        }
        
        return cliente;
    }
    
}

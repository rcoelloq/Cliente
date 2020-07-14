
package messenger;
import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;
public class Conector extends Thread{
    private Socket s;
    private ServerSocket ss;
    private InputStreamReader entradaSocket;
    private DataOutputStream salida;
    private BufferedReader entrada;
    final int puerto = 8180;
    
    //Conexion a una ip que le proporcionemos
    public Conector(String ip)
    {
        try{
            //Cliente conexion
            this.s = new Socket(ip,this.puerto);
            
            //Creacion de entrada de datos para lectura de mensajes
            this.entradaSocket = new InputStreamReader(s.getInputStream());
            this.entrada = new BufferedReader(entradaSocket);
            
            //Creacion de salida de datos para el envio de mensajes
            this.salida= new DataOutputStream(s.getOutputStream());
            this.salida.writeUTF("*Conectado* \n");
            
        }catch (Exception e){
            System.out.println("Ocurrio un error al tratar de abrir conexion hacia el servidor.");
            e.printStackTrace();
        };
    }
    
    //Para leerlo un hilo
    //Ejecutar permanente 
    public void run()
    {
        String texto;//Guardar todo lo que este recibiendo del servidor
        boolean conexion= false;
        while(true)
        {try{
            texto = entrada.readLine();
           
            if(texto.equals(" 1")){
            // abre el chat
                System.out.println("Se inica chat...");
                VCliente vc= new VCliente();
                vc.show();
                 conexion=true;     
            }if(texto.equals(" 0")){
                JOptionPane.showMessageDialog(null, null, 
                                              "Error de conexión, clave o usuario incorrecto", 
                                               JOptionPane.ERROR_MESSAGE);
                
                //mensaje
                 Messenger.cliente.enviarMSG("d");
            
             
            }
            if(conexion){
            
                 VCliente.jTextArea1.setText(VCliente.jTextArea1.getText()+"\n"+ texto);
            }
        }catch(IOException e){};
        }
    }
   
    public void enviarMSG(String msg)
    {
        System.out.println("enviado");
        try{
            this.salida = new DataOutputStream(s.getOutputStream());
            this.salida.writeUTF(msg+"\n");//Enviar un mensaje
        }catch (IOException e){
            System.out.println("Problema al enviar");
        };
    }
    public String leerMSG()
    {
        try{
            //leer una linea de texto que nos halla enviado el emisor
            return entrada.readLine();
        }catch(IOException e){};
        return null;
    }
    
}

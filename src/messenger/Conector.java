package messenger;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import util.Constantes;
import util.Utilitarios;

public class Conector extends Thread{
    public static Socket s;
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
            System.out.println("Ocurrio un error al tratar de abrir conexion hacia el servidor." + e);
        };
    }
    
    //Para leerlo un hilo
    //Ejecutar permanente 
    public void run()
    {
        String texto;//Guardar todo lo que este recibiendo del servidor
        boolean conexion= false;
        boolean grupos = false;
        String usuario= "";
        String etiquetaGrupos = "";
        
        VCliente cliente = new VCliente();
        
        while(true)
        {
            try{
                texto = entrada.readLine();
                texto =  texto.trim();
                String[] arrayValues = null;
                
                if (texto != null)
                {
                    arrayValues = Utilitarios.parsingString(texto);
                    //Inicio de Sexion, Conexion Usuario
                    if(arrayValues[0].equals(Constantes.etiquetaConexion) && !conexion)
                    {
                        String[] arrayNodos = Utilitarios.parsingStringNodes(arrayValues[1]);
                        if(Integer.parseInt(arrayNodos[0]) !=  0){
                            System.out.println("Se inica chat...");
                            Login lg = new Login();
                            lg.setVisible(false);//Oculto 
                            usuario = arrayNodos[1];
                            conexion=true;     
                        }if((Integer.parseInt(arrayNodos[0]) ==  0)){
                            JOptionPane.showMessageDialog(null, null, 
                                                          "Error de conexión, clave o usuario incorrecto", 
                                                           JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    
                    if(conexion && etiquetaGrupos != null && !grupos)
                    {
                         VCliente.lblUsuario.setText(usuario);
                         etiquetaGrupos = "GRP";
                         String trama   = etiquetaGrupos + "|" + usuario;
                         Messenger.cliente.enviarMSG(trama);
                         grupos = true;
                    }
                    
                    if(conexion &&  etiquetaGrupos != null &&  
                       Utilitarios.countPlotLetters(texto, '@') > 2 && grupos)
                    {   
                        //Grupos
                        //Si la lista es mayor uno es porque el usuario tiene asociado a mas de un grupo
                        arrayValues = Utilitarios.parsingString(texto.substring(1, texto.length()));
                        if((arrayValues.length > 2)  && conexion)
                        {   
                            ArrayList<String> listaGrupos = Utilitarios.getUserAssociatedGroups(arrayValues);
                            cliente.jComboBox1.removeAllItems();//Elimnar Items Por Default
                            cliente.jComboBox1.addItem(" ");
                            for (String item : listaGrupos) {
                                cliente.jComboBox1.addItem(item);
                            }
                            SwingUtilities.updateComponentTreeUI(cliente);//Reload
                            cliente.setVisible(true);//muestro 
                            conexion = false; // una sola vez ingresa a esta parte
                        }
                    }
                    
                    //Interaccion Mensajes
                    if(texto.contains(Constantes.etiquetaMensaje) && !conexion)
                    {
                        texto = texto.substring(3,texto.length());
                        VCliente.jTextArea1.setText(VCliente.jTextArea1.getText()+"\n"+ 
                                VCliente.grupoSeleccionado +": " +texto);   
                    }
                }
                
            }catch(IOException e){
                System.out.println("Ocurrio un error de lectura/Escrituria/Cliente: " + e);
            };
        }
    }
   
    public void enviarMSG(String msg)
    {
        System.out.println("MSG Enviado Emisor: "+msg);
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
    
    public void enviarDesconectar()
    {
        Messenger.cliente.enviarMSG("D");
    }
}

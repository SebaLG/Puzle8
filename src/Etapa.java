
import java.util.ArrayList;
import java.util.Scanner;


public class Etapa  {

    private ArrayList<Integer> datos = new ArrayList<Integer>();  //Aqui almacenaremos la posicion de los numeros en el juego
    private int profundidad;
    private Etapa padre;
    private int movimiento;
    private int coste;
    private final static ArrayList<Integer> objetivo = new ArrayList<Integer>(); //Posicion Objetivo


    public Etapa(int[] obje) {          //Constructor sin parametros, pasandole un objetivo


        ArrayList<Integer> repetidos = new ArrayList<Integer>();
        int aleatory;
        for (int i = 0; i < 9; i++) {
            do {
                aleatory = (int) (Math.random() * 9);       //Genero un Array Aleatorio con los Datos de la etapa
            }
            while (repetidos.contains(aleatory));
            repetidos.add(aleatory);
            datos.add(aleatory);
        }

        for (int j = 0; j < 9; j++) {               //Inicializo el objetivo
            objetivo.add(obje[j]);
        }

        profundidad = 0;
        movimiento = 0;
        padre = null;                       //Inicializo las variables
        coste=this.CalcularCoste();

    }

    public int[] obje(){        //Metodo que devuelve el Objetivo en un Array común
        int[] aux = new int[objetivo.size()];
        for(int i=0;i<objetivo.size();i++){
            aux[i]=objetivo.get(i);

        }
        return aux;
    }

    public static ArrayList<Integer> getObjetivo() {
        return (objetivo);
    }

    public Etapa(Etapa aux, int[] obje) {       //Constructor de copia


        for (int i = 0; i < 9; i++) {
            objetivo.add(obje[i]);
        }


        for (int i = 0; i < 9; i++) {
            datos.add(aux.get(i));
        }
        profundidad = aux.profundidad;
        movimiento = aux.movimiento;
        padre = aux.padre;
        this.coste=this.CalcularCoste();
    }

    public int CalcularCoste(){

        coste=this.estimacion();
    return coste; }

    public Etapa(int[] aux, int[] obje) {   //Constructor al que le paso un Array y inicializo el Estado a él


        datos = new ArrayList<Integer>();

        for (int i = 0; i < 9; i++) {
            objetivo.add(obje[i]);
        }


        for (int i = 0; i < 9; i++) {
            datos.add(aux[i]);
        }

        profundidad = 0;
        movimiento = 0;
        padre = null;
        coste=this.CalcularCoste();


    }

    public boolean iguales(Etapa aux) {

        boolean iguales = true;
        for (int i = 0; i < 9 && iguales==true; i++) {
            if (aux.get(i) != this.datos.get(i)) iguales = false;
        }
        return (iguales);

    }

    public boolean objetivo() {                 //Comparo si el estado es igual al estado objetivo, true si son iguales

        boolean iguales = true;
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) != objetivo.get(i)) iguales = false;
        }
        return iguales;


    }

    public void ver() {     //Muestra por pantalla el contenido del Array de Datos


        System.out.println(datos);


    }

    public void setprofundidad(int i) {
        profundidad = i;
    }

    public int getProfundidad(){return profundidad;}

    public void setpadre(Etapa aux) {
        padre = aux;
    }

    public void remove(int i) {
        datos.remove(i);
    }

    public void setmov(int i) {
        this.movimiento = i;
    }

    public Etapa mover(int mov) {       //Con este metodo creo un Estado auxiliar a partir del actual y este es el que muevo

        Etapa auxiliar = new Etapa(this, this.obje());

        auxiliar.setprofundidad(this.profundidad + 1); //Inicializo su profundidad
        auxiliar.setpadre(this);
        auxiliar.setmov(mov);
        int pos = datos.indexOf(0);     //Busco la posicion del 0 y a partir de esto delivero los posibles movimientos pasados por parametros
        int aux;

        if (mov == 1) {
            aux = auxiliar.get(pos - 3);
            auxiliar.remove(pos - 3);
            auxiliar.set(pos - 3, 0);
            auxiliar.remove(pos);
            auxiliar.set(pos, aux);
        }


        if (mov == 3) {
            aux = auxiliar.get(pos + 3);
            auxiliar.remove(pos + 3);
            auxiliar.set(pos + 3, 0);
            auxiliar.remove(pos);
            auxiliar.set(pos, aux);
        }
        if (mov == 2) {
            aux = auxiliar.get(pos + 1);
            auxiliar.remove(pos + 1);
            auxiliar.set(pos + 1, 0);
            auxiliar.remove(pos);
            auxiliar.set(pos, aux);
        }

        if (mov == 4) {
            aux = auxiliar.get(pos - 1);
            auxiliar.remove(pos - 1);
            auxiliar.set(pos - 1, 0);
            auxiliar.remove(pos);
            auxiliar.set(pos, aux);
        }

        return (auxiliar);  //Devuelvo la Etapa hija de la actual

    }

    public ArrayList<Integer> posibles() {

        ArrayList<Integer> movimientos = new ArrayList<Integer>();
        int pos = datos.indexOf(0); //Busco la posicion del 0, y añado al array movimientos los posibles movimientos

        switch (pos) {

            case 0:
                movimientos.add(2);
                movimientos.add(3);
                break;
            case 1:
                movimientos.add(2);
                movimientos.add(3);
                movimientos.add(4);
                break;
            case 2:
                movimientos.add(4);
                movimientos.add(3);
                break;
            case 3:
                movimientos.add(1);
                movimientos.add(3);
                movimientos.add(2);
                break;
            case 4:
                movimientos.add(1);
                movimientos.add(3);
                movimientos.add(2);
                movimientos.add(4);
                break;
            case 5:
                movimientos.add(1);
                movimientos.add(3);
                movimientos.add(4);
                break;
            case 6:
                movimientos.add(1);
                movimientos.add(2);
                break;
            case 7:
                movimientos.add(1);
                movimientos.add(2);
                movimientos.add(4);
                break;
            case 8:
                movimientos.add(1);
                movimientos.add(4);
                break;


        }

        return (movimientos);
    }

    public int get(int aux) {

        return datos.get(aux);


    }

    public void set(int aux, int i) {

        datos.add(aux, i);


    }

    public ArrayList<Etapa> gethijos() {    //Funcion que devuelve en un Array todos los hijos posibles del Estado actual

        Etapa aux = new Etapa(this,this.obje() );
        ArrayList<Etapa> listahijos = new ArrayList<Etapa>();

        ArrayList<Integer> movs = aux.posibles();
        int hijos = movs.size();

        for (int i = 0; i < hijos; i++) {
            listahijos.add(aux.mover(movs.get(i)));
        }
        return (listahijos);
    }

    public ArrayList<Etapa> tratar_repetidos(ArrayList<Etapa> hijos, ArrayList<Etapa> abiertos, ArrayList<Etapa> cerrados, int opcion) {    //Tratamiento de repetidos para la busqueda en Anchura


        ArrayList<Etapa> finales = new ArrayList<Etapa>();
        boolean repetido = false;
        int i=0, j=0;
        while(i<hijos.size()) //Recorro el Array Hijos
        {

            while(!repetido&&j<cerrados.size()) //Compruebo si esta en cerrados
            {
                if(hijos.get(i).iguales(cerrados.get(j)))
                {
                    repetido = true;

                }
                else
                    j++;
            }

            j=0;

            while(!repetido&&j<abiertos.size()) //Comprobuebo si esta en abiertos
            {
                if(hijos.get(i).iguales(abiertos.get(j)))
                {
                    repetido = true;

                }
                else
                    j++;
            }

            if(!repetido) //Si no esta en ninguna de las listas, lo añado a definitivos(finales)
            {
                finales.add(hijos.get(i));

            }
            if(opcion==2){
                if(!repetido){
                    System.out.println("Se añade el hijo " );
                    hijos.get(i).ver() ;
                    System.out.println( "a la lista abiertos." + "\n");
                }
                if(repetido){
                    System.out.println("El hijo ");
                    hijos.get(i).ver() ;
                    System.out.println( "esta repetido, se descarta." + "\n");
                }}


            i++;
            j=0;
            repetido = false;
        }


        return finales; //Devuelvo los hijos ya tratados


    }

    public ArrayList<Etapa> tratar_repetidos_greedy(ArrayList<Etapa> hijos, ArrayList<Etapa> abiertos, ArrayList<Etapa> cerrados, int opcion) {
        int i = 0;
        int j=0;
        boolean repetido=false;
        ArrayList<Etapa> finales = new ArrayList();
        while(i<hijos.size()) //Recorro el Array Hijos
        {

            while(!repetido&&j<cerrados.size()) //Compruebo si esta en cerrados
            {if(hijos.get(i).iguales(cerrados.get(j)))
                {
                    repetido = true;

                }
            else
                    j++;
            }
            j=0;
            while(!repetido&&j<abiertos.size()) //Comprobuebo si esta en abiertos
            {if(hijos.get(i).iguales(abiertos.get(j)))
                {
                    repetido = true;

                }
            else
                    j++;
            }
            if(!repetido) //Si no esta en ninguna de las listas, lo añado a definitivos(finales)
            {
                finales.add(hijos.get(i));
            }

            i++;
            j=0;
            repetido = false;

            if(opcion==2){
                if(!repetido){
                    System.out.println("Se añade el hijo " );
                    hijos.get(i).ver() ;
                    System.out.println( "a la lista abiertos." + "\n");
                }
                if(repetido){
                    System.out.println("El hijo ");
                    hijos.get(i).ver() ;
                    System.out.println( "esta repetido, se descarta." + "\n");
                }}

        }

        return finales;
    }

    public void show_menu(String Algoritmo, int opcion, int max, int maxcer, int nodosgenerados, long tiempo_total, Etapa actual) { //Un menu al que le paso los datos basicos y este se encarga de la presentacion segun la opcion pasada por parametro


        switch (opcion) {
            case 0: {
                System.out.println("Busqueda en: " + Algoritmo);
                System.out.println("Estado inicial: " + this.datos);
                System.out.println("Maximo valor del Array Abiertos: " + max);
                System.out.println("Maximo valor del Array Cerrados: " + maxcer);
                System.out.println("Nodos generados: " + nodosgenerados);
                System.out.println("Tiempo que ha tardado el Algoritmo: " + tiempo_total + " Milisegundos");
                System.out.println("Pasos " + actual.profundidad +"\n");
            }
            break;
            case 1: {
                System.out.println("Busqueda en: " + Algoritmo);
                System.out.println("Estado inicial: " + this.datos);
                System.out.println("Maximo valor del Array Abiertos: " + max);
                System.out.println("Maximo valor del Array Cerrados: " + maxcer);
                System.out.println("Nodos generados: " + nodosgenerados);
                System.out.println("Tiempo que ha tardado el Algoritmo: " + tiempo_total + " Milisegundos");
                System.out.println("Pasos " + actual.profundidad +"\n");
                Etapa nodo = actual;

                for (int i = 0; i < actual.profundidad; i++) {
                    int contador = 0;
                    int etapa = actual.profundidad - i;
                    System.out.println("Nodo numero " + etapa + ":");
                    for (int n = 0; n < 3; n++) {
                        ArrayList<Integer> auxiliar = new ArrayList<Integer>();
                        for (int j = 0; j < 3; j++) {

                            auxiliar.add(nodo.get(contador));
                            contador++;
                        }
                        System.out.println(auxiliar);
                    }
                    nodo = nodo.padre;
                }
            }
            break;
            case 2: {
                System.out.println("Busqueda en: " + Algoritmo);
                System.out.println("Estado inicial: " + this.datos);
                System.out.println("Maximo valor del Array Abiertos: " + max);
                System.out.println("Maximo valor del Array Cerrados: " + maxcer);
                System.out.println("Nodos generados: " + nodosgenerados);
                System.out.println("Tiempo que ha tardado el Algoritmo: " + tiempo_total + " Milisegundos");
                System.out.println("Pasos " + actual.profundidad +"\n");

                Etapa nodo = actual;

                for (int i = 0; i < actual.profundidad; i++) {
                    int contador = 0;
                    int etapa = actual.profundidad - i;
                    String movi = new String();
                    switch (nodo.movimiento) {
                        case 1:
                            movi = "Arriba";
                            break;
                        case 2:
                            movi = "Derecha";
                            break;
                        case 3:
                            movi = "Abajo";
                            break;
                        case 4:
                            movi = "Izquierda";
                            break;
                    }
                    System.out.println("Para llegar hasta aqui el nodo se ha desplazado a " + movi);
                    System.out.println("Se expande el nodo: " + nodo.datos);
                    for (int n = 0; n < 3; n++) {
                        ArrayList<Integer> auxiliar = new ArrayList<Integer>();
                        for (int j = 0; j < 3; j++) {

                            auxiliar.add(nodo.get(contador));
                            contador++;
                        }
                        System.out.println(auxiliar);
                    }
                    nodo = nodo.padre;
                }
            }


            break;
            default:
                System.out.println("Opcion erronea");
                break;
        }
    }

    public void menu(){
        System.out.println("El estado inicial es: ");
        this.ver();
        Scanner c;
        int opcion2;
        int opcion;
        System.out.println("¿Con que Algoritmo quiere realizar la busqueda? ");
        System.out.println("1- Anchura ");
        System.out.println("2- Profundidad ");
        System.out.println("3- Profundidad Iterativa");
        System.out.println("4- Greedy ");
        System.out.println("5- A* Estrella ");
        System.out.println("Eleccion: ");
        c=new Scanner(System.in);
        opcion=c.nextInt();
        System.out.println("¿Con que opcion quiere visualizar los datos? (0-1-2) ");
        c=new Scanner(System.in);
        opcion2=c.nextInt();

        if((opcion>0 && opcion<6)&&(opcion2>-1 && opcion2<3)) {
            switch (opcion) {
                case 1: {Etapa hija = this.anchura(opcion2);}
                break;
                case 2: {Etapa hija = this.profundidad(10, opcion2);}
                break;
                case 3:{
                    Etapa hija = this.profundidaditerativa(10, opcion2);}
                break;
                case 4:{
                    Etapa hija = this.Greedy(opcion2);}
                break;
                case 5:{
                    Etapa hija = this.estrella(opcion2);}
                break;
                default:
                    System.out.println("Opcion erronea");
            }
        }

        else System.out.println("Opcion erronea");

    }

    public Etapa anchura(int opcion) {  //Busqueda en Anchura

        long tiempo_inicial=System.currentTimeMillis();
        long tiempo_final;
        long tiempo_total;
        boolean encontrado = false;
        ArrayList<Etapa> abiertos = new ArrayList<Etapa>();
        ArrayList<Etapa> cerrados = new ArrayList<Etapa>();
        abiertos.add(this);
        int max = 0;
        int maxcer = 0;
        int nodosgenerados = 0;
        abiertos.add(null);
        Etapa actual = this;

        while(!(actual.objetivo())&&!(abiertos.isEmpty()))
        {
            if(abiertos.size()>max) //Para tener constancia del tamaño maximo de abiertos
                max = abiertos.size();
            if(cerrados.size()>maxcer) //Para tener constancia del tamaño maximo de cerrados
                maxcer = cerrados
                        .size();
            abiertos.remove(null); //Remuevo el elemento null ya que no es necesario
            ArrayList<Etapa> hijos = actual.gethijos(); //Genero hijos

            abiertos.addAll(tratar_repetidos(hijos,abiertos,cerrados, opcion)); //Trato repetidos
            cerrados.add(actual); //Añado a cerrados el nodo actual y lo quito posteriormente de abiertos
            actual = abiertos.get(0);
            abiertos.remove(0);
            nodosgenerados++;

        }


        tiempo_final=System.currentTimeMillis();
        tiempo_total=tiempo_final-tiempo_inicial;

        this.show_menu("Anchura",opcion,max,maxcer,nodosgenerados,tiempo_total,actual);

        return (actual);

    }

    public Etapa profundidad(int profundidadx, int opcion) { //Algoritmo de la Busqueda en Profundidad

        long tiempo_inicial=System.currentTimeMillis();
        long tiempo_final;
        long tiempo_total;
        boolean encontrado = false;
        ArrayList<Etapa> abiertos = new ArrayList<Etapa>();
        ArrayList<Etapa> cerrados = new ArrayList<Etapa>();


        int Max=0;
        int Maxcer=0;
        int nodosgenerados=0;


        Etapa actual = this;
        abiertos.add(actual);
        while ((!actual.objetivo()) && (!abiertos.isEmpty())) {

            abiertos.remove(abiertos.size()-1);
            cerrados.add(actual);
            if (actual.profundidad <= profundidadx) {     //Comparo para ver si ha llegado a la profundidad pasada por parametro
                ArrayList<Etapa> Hijos = actual.gethijos();
                ArrayList<Etapa> finales = tratar_repetidos_profundidad(Hijos, abiertos, cerrados, opcion); //Trato repetidos
                int repeticiones=tratar_repetidos_profundidad(Hijos, abiertos, cerrados, opcion).size();
                nodosgenerados=nodosgenerados+repeticiones;
                if (finales.size() != 0) {

                        abiertos.addAll(finales);
                    }
                }

            if (!abiertos.isEmpty()) {
                actual = abiertos.get(abiertos.size() - 1);
            }
            if (abiertos.size() > Max) {
                Max = abiertos.size();
            }
            if (cerrados.size() > Maxcer) {
               Maxcer = cerrados.size();
            }


        }tiempo_final=System.currentTimeMillis();
        tiempo_total=tiempo_final-tiempo_inicial;

        this.show_menu("Profundidad",opcion,Max,Maxcer,nodosgenerados,tiempo_total,actual);

        return (actual);

    }

    public Etapa profundidaditerativa(int limite, int opcion){

        long tiempo_inicial=System.currentTimeMillis();
        long tiempo_final;
        long tiempo_total;
        int prof=1;

        ArrayList<Etapa> abiertos = new ArrayList<Etapa>();
        ArrayList<Etapa> cerrados = new ArrayList<Etapa>();
        int Max=0;
        int Maxcer=0;
        int nodosgenerados=0;
        int profundidad = 1;



        ArrayList<Etapa> aux = new ArrayList<Etapa>();

        Etapa actual = this;

        do
        {

            abiertos.clear(); //Limpio la lista de abiertos
            cerrados.clear(); //Limpio la lista de cerrados
            abiertos.add(null);
            actual = this;


            while(!(actual.objetivo())&&!(abiertos.isEmpty()))
            {
                if(abiertos.size()>Max) //Comprobacion del Maximo valor de abiertos
                    Max = abiertos.size();

                if(cerrados.size()>Maxcer) Maxcer=cerrados.size();

                abiertos.remove(null);
                if(actual.getProfundidad()<=profundidad) //Comprobacion de la profundidad actual
                {
                    ArrayList<Etapa> hijos = actual.gethijos(); //Aumento la profundidad

                    abiertos.addAll(tratar_repetidos_profundidad(hijos, abiertos, cerrados, opcion));
                }



                cerrados.add(actual);

                actual = abiertos.get(abiertos.size()-1);
                abiertos.remove(abiertos.size()-1);
                        nodosgenerados++;

            }
            profundidad++; //Aumento la profundidad con la que llamo a Profundidad
        }
        while(!actual.objetivo()&&profundidad<=limite);

        tiempo_final=System.currentTimeMillis();
        tiempo_total=tiempo_final-tiempo_inicial;

        this.show_menu("Profundidad Iterativa",opcion,Max,Maxcer,nodosgenerados,tiempo_total,actual);

    return(actual);}

    public Etapa Greedy(int opcion) {   long tiempo_inicial=System.currentTimeMillis();

        long tiempo_final;
        long tiempo_total;
        ArrayList<Etapa> abiertos = new ArrayList();
        ArrayList<Etapa> cerrados = new ArrayList();
        Etapa actual;
        int Max=0;
        int Maxcer=0;
        int nodosgenerados=0;
        int contador2=1;

        ArrayList<Etapa> hijos;

        //Algoritmo de busqueda Greedy

        abiertos.add(null);
        actual = this;
        while (!actual.objetivo() && !abiertos.isEmpty()) {

            abiertos.remove(0);
            abiertos.remove(null);
            cerrados.add(actual);
            hijos = actual.gethijos();
            ArrayList<Etapa> finales = actual.tratar_repetidos_greedy(hijos, abiertos, cerrados, opcion);
            abiertos.addAll(finales);
            nodosgenerados=nodosgenerados+finales.size();
            if(abiertos.size()>Max)Max=abiertos.size();
            if(cerrados.size()>Maxcer)Maxcer=cerrados.size();

            abiertos = actual.ordena(abiertos);
            actual = abiertos.get(0);
            contador2++;
        }

        if (actual.objetivo()) {
            System.out.println("La solucion esta a profundidad " + actual.profundidad+"\n");
            actual.ver();
        } else
            System.out.println("No se ha encontrado solucion.");

        tiempo_final=System.currentTimeMillis();
        tiempo_total=tiempo_final-tiempo_inicial;

        this.show_menu("Greedy",opcion,Max,Maxcer,nodosgenerados,tiempo_total,actual);
        return actual;
    }

    public ArrayList<Etapa> ordena( ArrayList<Etapa> aux){ //Metodo ordenar para ordenar la lista de abiertos segun el coste

        for(int i=0;i<aux.size();i++){
            for(int j=i;j<aux.size();j++){
                if(aux.get(i).coste>aux.get(j).coste) {

                    Etapa auxiliar = aux.get(i);
                    aux.add(i, aux.get(j));
                    aux.remove(i);

                    aux.add(j, auxiliar);
                    aux.remove(j);
                }
                }
                }


    return aux;
    }

    public int coste_estrella(){

        //Calculo del coste de un Estado para el Algoritmo estrella

        return estimacion() + this.getProfundidad();
    }

    public ArrayList<Etapa> ordenaestrella( ArrayList<Etapa> aux){





        Etapa auxi;         //Ordenacion del array abieertos para el algoritmo Estrella
        for(int i=0; i<aux.size()-1; i++)
        {
            for(int j=aux.size() - 1; j>i; j--)
            {
                if(aux.get(j).coste_estrella()<aux.get(j-1).coste_estrella())
                {
                    auxi = aux.get(j);
                    aux.set(j, aux.get(j-1));
                    aux.set(j-1, auxi);
                }
            }
        }
        return aux;
    }

    public int estimacion(){

        int coste = 0;
        int[][] objetivo = {{1,2,3},{4,5,6},{7,8,0}};
        int distancia = 0;
        int [][] aux = new int[3][3];
        int contador = 0;
        for(int i=0; i<3; i++) //Copio a un array bidimensional
        {
            for(int j=0; j<3; j++)
            {
                aux[i][j] = datos.get(contador);
                contador++;
            }
        }
        for(int i=0; i<3; i++) //Aplico la heuristica Manhattan, la cual he buscado de internet para la optimizacion de codigo
        {
            for(int j=0; j<3; j++)
            {
                switch(aux[j][i])
                {
                    case 1:
                        distancia = i+j;
                        break;
                    case 2:
                        distancia = Math.abs(i-1) + j;
                        break;
                    case 3:
                        distancia = Math.abs(i-2) + j;
                        break;
                    case 4:
                        distancia = i + Math.abs(j-1);
                        break;
                    case 5:
                        distancia = Math.abs(i-1) + Math.abs(j-1);
                        break;
                    case 6:
                        distancia = Math.abs(i-2) + Math.abs(j-1);
                        break;
                    case 7:
                        distancia = i + Math.abs(j-2);
                        break;
                    case 8:
                        distancia = Math.abs(i-1) + Math.abs(j-2);
                        break;
                    case 0:
                        distancia = Math.abs(i-2) + Math.abs(j-2);
                        break;
                }
                coste = coste + distancia;
                if(aux[i][j]!=objetivo[i][j])
                    coste++;
            }
        }

        return coste;
    }

    public ArrayList<Etapa> tratar_repetidos_estrella(ArrayList<Etapa> hijos, ArrayList<Etapa> abiertos, ArrayList<Etapa> cerrados, int opcion){
        {
            ArrayList<Etapa> finalHijos = new ArrayList<Etapa>();
            boolean repetido = false;
            int i=0, j=0;
            while(i<hijos.size())
            {

                while(!repetido&&j<cerrados.size()) //Comprobacion de que esta en cerrados
                {
                    if(hijos.get(i).iguales(cerrados.get(j)))
                    {
                        repetido = true;

                        if(hijos.get(i).coste_estrella()<cerrados.get(j).coste_estrella())
                        {
                            finalHijos.add(hijos.get(i));

                        }
                    }
                    else
                        j++;
                }

                j=0;

                while(!repetido&&j<abiertos.size()) //Comprobacion en abiertos
                {
                    if(hijos.get(i).iguales(abiertos.get(j)))
                    {
                        repetido = true;

                        if(hijos.get(i).coste_estrella()<abiertos.get(j).coste_estrella())
                        {
                            cerrados.add(abiertos.get(j));
                            abiertos.remove(j);
                            abiertos.set(j, hijos.get(i));


                        }
                    }
                    else
                        j++;
                }

                if(repetido==false)
                {
                    finalHijos.add(hijos.get(i));

                }
                if(opcion==2){
                    if(!repetido){
                        System.out.println("Se añade el hijo " );
                        hijos.get(i).ver() ;
                        System.out.println( "a la lista abiertos." + "\n");
                    }
                    if(repetido){
                        System.out.println("El hijo ");
                        hijos.get(i).ver() ;
                        System.out.println( "esta repetido, se descarta." + "\n");
                    }}


                i++;
                j=0;
                repetido = false;
            }

            return finalHijos;
        }
    }

    public ArrayList<Etapa> tratar_repetidos_profundidad(ArrayList<Etapa> hijos, ArrayList<Etapa> abiertos, ArrayList<Etapa> cerrados, int opcion){

        ArrayList<Etapa> finales = new ArrayList<Etapa>();
        boolean repetido = false;
        int i=0, j=0;
        while(i<hijos.size()) //Trato sobre aux
        {

            while(!repetido&&j<cerrados.size()) //Comprobuebo cerrados
            {
                if(hijos.get(i).iguales(cerrados.get(j)))
                {
                    repetido = true;
                }
                else
                    j++;
            }

            j=0;

            while(!repetido&&j<abiertos.size()) //Compruebo abiertos
            {
                if(hijos.get(i).iguales(abiertos.get(j)))
                {
                    repetido = true;
                        if(hijos.get(i).profundidad<abiertos.get(j).profundidad){
                            cerrados.add(abiertos.get(j));
                            abiertos.remove(abiertos.get(j));
                            abiertos.add(j,hijos.get(i));
                        }

                }
                else
                    j++;
            }

            if(!repetido) //Finalmente se a�ade a la lista final de Nodos, de la misma forma que
            {
                finales.add(hijos.get(i));

            }

            if(opcion==2){
            if(!repetido){
            System.out.println("Se añade el hijo " );
                hijos.get(i).ver() ;
                System.out.println( "a la lista abiertos." + "\n");
        }
            if(repetido){
                System.out.println("El hijo ");
                hijos.get(i).ver() ;
                System.out.println( "esta repetido, se descarta." + "\n");
            }}


            i++;
            j=0;
            repetido = false;
        }

        return finales;
    }

    public Etapa estrella(int opcion){

        ArrayList<Etapa> abiertos = new ArrayList();
        ArrayList<Etapa> cerrados = new ArrayList();
        long tiempo_inicial=System.currentTimeMillis();
        long tiempo_final;
        long tiempo_total;
        int Max=0;
        int Maxcer=0;
        int nodosgenerados=0;
        Etapa actual=this;
        abiertos.add(null);

        while(!(actual.objetivo())&&!(abiertos.isEmpty()))
        {
            if(abiertos.size()>Max)  Max = abiertos.size();
            if(cerrados.size()>Maxcer) Maxcer=cerrados.size();
            abiertos.remove(null);
            ArrayList<Etapa> hijos = actual.gethijos(); //Genero hijos

            abiertos.addAll(tratar_repetidos_estrella(hijos, abiertos, cerrados, opcion)); //Trato repetidos para el caso Estrella
            abiertos.remove(0);
            ordenaestrella(abiertos); //Ordeno por coste en abiertos, para establecer una cola con prioridad
            cerrados.add(actual); //Añado a cerrados el Nodo tratado
            actual= abiertos.get(0);
                        nodosgenerados++;

        }

        tiempo_final=System.currentTimeMillis();
        tiempo_total=tiempo_final-tiempo_inicial;

        this.show_menu("A* Estrella",opcion,Max,Maxcer,nodosgenerados,tiempo_total,actual);

        return actual;

    }

    public ArrayList<String> familia() { //A partir de un Estado devuelve en un Array los movimientos necesarios para llegar hasta él

        ArrayList<String> movimientos = new ArrayList<String>();
        Etapa aux = new Etapa(this, this.obje());
        String mov = new String();
        switch (this.movimiento) {
            case 1:
                mov = "Arriba";
                break;
            case 2:
                mov = "Derecha";
                break;
            case 3:
                mov = "Abajo";
                break;
            case 4:
                mov = "Izquierda";
                break;
        }
        movimientos.add(mov);

        for (int i = 0; i < this.profundidad; i++) {
            aux = aux.padre;
            String movi = new String();
            switch (aux.movimiento) {
                case 1:
                    movi = "Arriba";
                    break;
                case 2:
                    movi = "Derecha";
                    break;
                case 3:
                    movi = "Abajo";
                    break;
                case 4:
                    movi = "Izquierda";
                    break;
            }
                movimientos.add(movi);


            }


        return (movimientos);

        }
    }

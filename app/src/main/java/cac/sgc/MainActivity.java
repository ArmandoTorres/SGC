package cac.sgc;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.Menu;
import android.app.Fragment;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

import com.delacrmi.controller.EntityManager;

import cac.sgc.entities.Caniales;
import cac.sgc.entities.Empleados;
import cac.sgc.entities.Empresas;
import cac.sgc.entities.Fincas;
import cac.sgc.entities.Frentes;
import cac.sgc.entities.Lotes;
import cac.sgc.entities.Rangos;
import cac.sgc.entities.Transaccion;
import cac.sgc.entities.Vehiculos;
import cac.sgc.fragments.Formulario1;
import cac.sgc.fragments.Formulario2;
import cac.sgc.fragments.Formulario3;
import cac.sgc.fragments.Formulario4;
import cac.sgc.fragments.Home;
import cac.sgc.fragments.Listado;
import cac.sgc.fragments.SyncFragment;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    //Referencias en pantalla
    private ImageButton btnCrearRegistro, btnMostrarListas, btnHome, btnAnterior, btnSiguiente;
    private Toolbar toolbar;
    private TextView tvTitle;
    private FragmentManager manejador;

    //Fragmentos a utilizar.
    private Formulario1 formulario1;
    private Formulario2 formulario2;
    private Formulario3 formulario3;
    private Formulario4 formulario4;
    private Listado listadoRegistros;
    private Home home;

    private SyncFragment syncFragment;
    private FloatingActionButton fab_app;
    private EntityManager entityManager;


    //Layout's en pantalla.
    private GridLayout gridLayoutNextBack;
    private GridLayout glyMainMenu;

    //Variables de control.
    private String ultimoFragmentoCargado;

    private View.OnClickListener onClickItem;

    // <editor-fold defaultstate="collapsed" desc="Metodos sobre cargados">

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //crearmos el manejador de fragmentos
        manejador = this.getFragmentManager();

        inicializarComponentes();
        inicializarMetodos();

        if(savedInstanceState == null)
            cargarFragmento(getHome(),null);
        else
            startActivity(savedInstanceState.getString("UltimoFragmentoCargado"));

        configurarBaseDatos();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("UltimoFragmentoCargado", ultimoFragmentoCargado);
        super.onSaveInstanceState(outState);
    }

    private void startActivity(String fragmentName){

        if ( fragmentName == null ) {
            cargarFragmento(getHome(),null);
        } else {
            try{
                switch ( fragmentName.toUpperCase() ){
                    case "FORMULARIO1": cargarFragmento(getFormulario1(),null); break;
                    case "FORMULARIO2": cargarFragmento(getFormulario2(),null); break;
                    case "FORMULARIO3": cargarFragmento(getFormulario3(),null); break;
                    case "FORMULARIO4": cargarFragmento(getFormulario4(),null); break;
                    case "LISTADO": cargarFragmento(getListadoRegistros(),null); break;
                    case "SYNCFRAGMENT":
                        tvTitle.setText(R.string.sync_name);
                        cargarFragmento(getSyncFragment(),"sync");
                        fab_app.setVisibility(View.VISIBLE);
                        gridLayoutManager(glyMainMenu, View.INVISIBLE);
                        break;
                    case "HOME": cargarFragmento(getHome(),null); break;
                }
            } catch (Exception e) {
                cargarFragmento(getHome(),null);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_sync_menu:
                ActionMenuItemView btn;
                if (ultimoFragmentoCargado.equals(SyncFragment.class.getSimpleName())) {
                    startActivity(Home.class.getSimpleName().toUpperCase());
                    btn = (ActionMenuItemView ) findViewById(R.id.btn_sync_menu);
                    btn.setIcon(MainActivity.this.getResources().getDrawable(R.drawable.actualizar, getTheme()));
                    //btn.setBackgroundResource(R.drawable.actualizar);

                }else{
                    startActivity(SyncFragment.class.getSimpleName().toUpperCase());
                    btn = (ActionMenuItemView ) findViewById(R.id.btn_sync_menu);
                    btn.setIcon(MainActivity.this.getResources().getDrawable(R.drawable.home, getTheme()));
                    //btn.setBackgroundResource(R.drawable.home);
                }
                break;
        }
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent evt) {
        //Conseguimos el boton que disparo el evento.
        ImageButton btn = (ImageButton) v;
        switch( evt.getActionMasked() ){
            case MotionEvent.ACTION_DOWN:
                cambiarFragmento(btn);
                break;
        }
        return true;
    }

    private void events(){
        onClickItem = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.fab_app:
                        if(manejador.findFragmentByTag("sync").getClass().getSimpleName().
                                equals(SyncFragment.class.getSimpleName()))
                            Toast.makeText(MainActivity.this,"actualizar todas las tablas",Toast.LENGTH_SHORT).show();
                        getSyncFragment().syncAllTables();
                        break;
                }
            }
        };
    }

    // </editor-fold>

    private void inicializarComponentes() {

        events();

        try {
            //Enlazamos los objetos
            btnCrearRegistro = (ImageButton) this.findViewById(R.id.btn_crear_registro);
            btnMostrarListas = (ImageButton) this.findViewById(R.id.btn_mostrar_listas);
            btnAnterior = (ImageButton) this.findViewById(R.id.btnAnterior);
            btnSiguiente = (ImageButton) this.findViewById(R.id.btnSiguiente);
            btnHome = (ImageButton) this.findViewById(R.id.btn_home);

            gridLayoutNextBack = (GridLayout) this.findViewById(R.id.gridLayoutBtnNextBack);
            glyMainMenu = (GridLayout) findViewById(R.id.gridLayoutBtnHomeNewList);

            fab_app = (FloatingActionButton) findViewById(R.id.fab_app);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fab_app.setBackgroundTintList(getResources().getColorStateList(R.color.fab_color,getTheme()));
            }
            fab_app.setOnClickListener(onClickItem);

            //Inicializamos el action bar.
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");

            tvTitle = (TextView)findViewById(R.id.txtAbTitulo);
            tvTitle.setText(R.string.title);
            tvTitle.setTextSize(17f);

        } catch (Exception e) {
            Log.e("InicializarComponentes", "Error: "+e.getMessage());
            e.printStackTrace();
            Toast.makeText(this,"Ocurrio un error al inicializar los componentes", Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializarMetodos( ) {
        try {
            //Agregamos el touchListener que es esta misma clase.
            btnCrearRegistro.setOnTouchListener(this);
            btnMostrarListas.setOnTouchListener(this);
            btnAnterior.setOnTouchListener(this);
            btnSiguiente.setOnTouchListener(this);
            btnHome.setOnTouchListener(this);
        } catch (Exception e) {
            Log.e("InicializarMetodos", "Error: "+e.getMessage());
            e.printStackTrace();
            Toast.makeText(this,"Ocurrio un error al inicializar los metodos", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarFragmento(Fragment fragmento, String tag) {
        try {
            // Guardamos el ultimo fragmento cargado.
            ultimoFragmentoCargado = fragmento.getClass().getSimpleName();

            // Decidimos si mostrar los botones de pasar al siguiente formulario o ocultarlos.
            if (ultimoFragmentoCargado.equals("Home") ||
                    ultimoFragmentoCargado.equals("Listado") ||
                    ultimoFragmentoCargado.equals("SyncFragment")) {
                gridLayoutManager(gridLayoutNextBack, View.INVISIBLE);
            } else if (ultimoFragmentoCargado.equals("Formulario1")) {
                gridLayoutManager(gridLayoutNextBack, View.VISIBLE);
                btnAnterior.setVisibility(View.INVISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                btnSiguiente.setImageResource(R.drawable.siguiente);
            } else if (ultimoFragmentoCargado.equals("Formulario2") || ultimoFragmentoCargado.equals("Formulario3")) {
                gridLayoutManager(gridLayoutNextBack, View.VISIBLE);
                btnAnterior.setVisibility(View.VISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                btnSiguiente.setImageResource(R.drawable.siguiente);
            } else if (ultimoFragmentoCargado.equals("Formulario4")) {
                gridLayoutManager(gridLayoutNextBack, View.VISIBLE);
                btnAnterior.setVisibility(View.VISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                btnSiguiente.setImageResource(R.drawable.grabar);
            }

            //creamos la transaccion para cargar el fragmento.
            FragmentTransaction transaccion = manejador.beginTransaction();
            //Realizamos el reemplazo del fragmento.
            if(tag != null && !tag.equals("") && !tag.equals(" "))
                transaccion.replace(R.id.contenedor_fragments,fragmento,tag);
            else
                transaccion.replace(R.id.contenedor_fragments, fragmento);

            if(fab_app.getVisibility() == View.VISIBLE) {
                fab_app.setVisibility(View.INVISIBLE);
                gridLayoutManager(glyMainMenu, View.VISIBLE);
            }

            //Hacemos efectivo el cambio.
            transaccion.commit();

        } catch (Exception e){
            Log.e("CargarFragmento", "Error al cargar el fragmento.",e);
            Toast.makeText(this,"Ocurrio un error al cargar el fragmento.", Toast.LENGTH_SHORT).show();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Get's and Set's">
    public Formulario1 getFormulario1() {
        if ( formulario1 == null ) {
            formulario1 = Formulario1.init(this);
        }
        return formulario1;
    }

    public Formulario2 getFormulario2(){
        if ( formulario2 == null ) {
            formulario2 = Formulario2.init(this);
        }
        return formulario2;
    }

    public Formulario3 getFormulario3(){
        if ( formulario3 == null ) {
            formulario3 = Formulario3.init(this);
        }
        return formulario3;
    }

    public Formulario4 getFormulario4(){
        if ( formulario4 == null ) {
            formulario4 = Formulario4.init(this);
        }
        return formulario4;
    }

    public Home getHome() {
        if ( home == null ) home = Home.init(this);
        return home;
    }

    public Listado getListadoRegistros() {
        if ( listadoRegistros == null ) listadoRegistros = listadoRegistros.init(this);
        return listadoRegistros;
    }

    public EntityManager getEntityManager() {
        if ( entityManager == null ) {
            entityManager = new EntityManager(this,
                                              getResources().getString(R.string.DATA_BASE_NAME),
                                              null,
                                              Integer.parseInt(getResources().getString(R.string.DATA_BASE_VERSION)));
        }
        return entityManager;
    }

    public SyncFragment getSyncFragment(){
        if(syncFragment == null)
            syncFragment = SyncFragment.init(this,entityManager,"http://100.10.20.164:3000");
        return syncFragment;
    }

    // </editor-fold>

    private void cambiarFragmento(View view) {
        try {
            if ( !(view == null) ) {
                switch (view.getId()) {
                    case R.id.btn_crear_registro: cargarFragmento(getFormulario1(),null); break;
                    case R.id.btn_mostrar_listas: cargarFragmento(getListadoRegistros(),null); break;
                    case R.id.btn_home:
                        if (!ultimoFragmentoCargado.isEmpty() && !ultimoFragmentoCargado.equals("Home")) {
                            new AlertDialog.Builder(this)
                                    .setMessage("¿Esta seguro(a) de volver al menú principal?")
                                    .setCancelable(false)
                                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            cargarFragmento(getHome(),null);
                                            clearForms();
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                        break;
                    case R.id.btnAnterior:
                        switch (ultimoFragmentoCargado.toUpperCase()) {
                            case "FORMULARIO2": cargarFragmento(getFormulario1(),null); break;
                            case "FORMULARIO3": cargarFragmento(getFormulario2(),null); break;
                            case "FORMULARIO4": cargarFragmento(getFormulario3(),null); break;
                        }
                        break;
                    case R.id.btnSiguiente:
                        switch (ultimoFragmentoCargado.toUpperCase()) {
                            case "FORMULARIO1":
                                if ( formulario1.validateForm() )
                                    cargarFragmento(getFormulario2(),null);
                                break;
                            case "FORMULARIO2":
                                if ( formulario2.validateForm() )
                                    cargarFragmento(getFormulario3(),null);
                                break;
                            case "FORMULARIO3":
                                if ( formulario3.validateForm() )
                                    cargarFragmento(getFormulario4(),null);
                                break;
                            case "FORMULARIO4":
                                if ( formulario1.validateForm() && formulario2.validateForm() && formulario3.validateForm() && formulario4.validateForm() ) {
                                    if  ( grabarFormularios() ) {
                                        Toast.makeText(this, "El formulario ha sido cargado correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                    cargarFragmento(getHome(),null);
                                }
                                break;
                        }
                        break;
                }
            }
        } catch (Exception e) {
            Log.e("Cargar Fragmento.","Error: "+e.getMessage());
            e.printStackTrace();
            Toast.makeText(this,"Ocurrio un error al cambiar de pagina.", Toast.LENGTH_SHORT).show();
        }
    }

    protected Boolean grabarFormularios() {

        try {
            if (formulario1 != null && formulario2 != null && formulario3 != null && formulario4 != null) {

                Transaccion transaccion = new Transaccion().entityConfig();

                transaccion.setValue(Transaccion.FRENTE_CORTE, formulario1.getFteCorte().getText().toString());
                transaccion.setValue(Transaccion.FRENTE_ALCE, formulario1.getFteAlce().getText().toString());
                transaccion.setValue(Transaccion.ID_FINCA, formulario1.getFinca().getText().toString());
                transaccion.setValue(Transaccion.ID_CANIAL, formulario1.getCanial().getText().toString());
                transaccion.setValue(Transaccion.ID_LOTE, formulario1.getLote().getText().toString());

                //formulario 2
                transaccion.setValue(Transaccion.ORDEN_QUEMA, formulario2.getOrdenQuema().getText().toString());
                transaccion.setValue(Transaccion.FECHA_CORTE, formulario2.getFechaCorte());
                transaccion.setValue(Transaccion.CLAVE_CORTE, formulario2.getListaClaveCorte().getSelectedItem().toString());
                transaccion.setValue(Transaccion.CODIGO_CABEZAL, formulario2.getListaCabezales().getSelectedItem().toString());
                transaccion.setValue(Transaccion.CONDUCTOR_CABEZAL, formulario2.getEditTextConductorCabezal().getText().toString());

                //Formulario 3.
                transaccion.setValue(Transaccion.CODIGO_CARRETA, formulario3.getListaCodigoCarreta().getSelectedItem().toString());
                transaccion.setValue(Transaccion.CODIGO_COSECHADORA, formulario3.getListaCodigoCosechadora().getSelectedItem().toString());
                transaccion.setValue(Transaccion.OPERADOR_COSECHADORA, formulario3.getEditTextConductorCosechadora().getText().toString());
                transaccion.setValue(Transaccion.CODIGO_TRACTOR, formulario3.getListaCodigoTractor().getSelectedItem().toString());
                transaccion.setValue(Transaccion.OPERADOR_TRACTOR, formulario3.getEditTextConductorTractor().getText().toString());

                //formulario 4.
                transaccion.setValue(Transaccion.CODIGO_APUNTADOR, formulario4.getEditTextCodigoApuntador().getText().toString());
                transaccion.setValue(Transaccion.CODIGO_VAGON, formulario4.getListaCodigoVagones().getSelectedItem().toString());

                Log.e("Campos: ", "Campos: " + getEntityManager().findOnce(Transaccion.class,"*",null,null));

                Rangos rango = (Rangos) getEntityManager().findOnce(Rangos.class,"max("+Rangos.ENVIO_ACTUAL+")+1 "+Rangos.ENVIO_ACTUAL,""+Rangos.DISPOSITIVO+" = 'Dispositivo 1'",null);

                transaccion.setValue(Transaccion.NO_ENVIO, rango.getColumnValueList().getAsString(Rangos.ENVIO_ACTUAL));

                transaccion = (Transaccion) getEntityManager().save(transaccion);

                Log.e("Resultado","Valor del PK: "+transaccion.getColumnValueList().getAsString(Transaccion.CORRELATIVO));

            }
            return true;
        } catch (Exception e){
            Log.e("Error", "Metodo grabar formularios.", e);
            Toast.makeText(this, "Ocurrio un error al grabar la información.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Metodo utilizado para ocultar o mostrar un layout en especifico.
     * @param gridLayout GridLayout a modificar.
     * @param pIndicador Bandera que indica si se va a mostrar el grid o se va a ocultar.
     * */
    private void gridLayoutManager(GridLayout gridLayout, int pIndicador) {
        try {
            if (gridLayout != null) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) gridLayout.getLayoutParams();
                switch (pIndicador) {
                    case View.VISIBLE:
                        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                        gridLayout.invalidate();
                        break;
                    case View.INVISIBLE:
                        params.height = 0;
                        gridLayout.invalidate();
                        break;
                }
            }
        } catch (Exception e) {
            Log.e("GridLayoutManager", "Error: "+e.getMessage());
            e.printStackTrace();
            Toast.makeText(this,"Ocurrio un error al modificar el gridLayout.",Toast.LENGTH_SHORT).show();
        }
    }

    private void clearForms(){
        if ( formulario1 != null) {
            formulario1.clearForm();
        }
        if ( formulario2 != null) {
            formulario2.clearForm();
        }
        if ( formulario3 != null) {
            formulario3.clearForm();
        }
        if ( formulario4 != null) {
            formulario4.clearForm();
        }
    }

    private void configurarBaseDatos () {

        getEntityManager().addTable(Fincas.class);
        getEntityManager().addTable(Caniales.class);
        getEntityManager().addTable(Frentes.class);
        getEntityManager().addTable(Empresas.class);
        getEntityManager().addTable(Vehiculos.class);
        getEntityManager().addTable(Lotes.class);
        getEntityManager().addTable(Empleados.class);
        getEntityManager().addTable(Rangos.class);
        getEntityManager().addTable(Transaccion.class);
        getEntityManager().init();

        /*Fincas fincas = new Fincas().entityConfig();
        fincas.setValue(Fincas.FINCA,"1");
        fincas.setValue(Fincas.DESCRIPCION, "Santana");
        fincas = (Fincas) getEntityManager().save(fincas);

        Log.e("Finca: ", "Fincas: " + fincas.getColumnValueList().getAsString(Fincas.FINCA));

        Caniales caniales = new Caniales().entityConfig();
        caniales.setValue(Caniales.ID_FINCA, fincas.getColumnValueList().getAsString(Fincas.FINCA));
        caniales.setValue(Caniales.DESCRIPCION, "SANTA CRUZ # 306");
        caniales = (Caniales) getEntityManager().save(caniales);

        //Log.e("Caniales: ", "Caniales: "+caniales.getColumnValueList().getAsString(Caniales.CANIAL));

        Lotes lotes = new Lotes().entityConfig();
        lotes.setValue(Lotes.ID_FINCA, fincas.getColumnValueList().getAsString(Fincas.FINCA));
        lotes.setValue(Lotes.ID_CANIAL, caniales.getColumnValueList().getAsString(Caniales.CANIAL));
        lotes.setValue(Lotes.DESCRIPCION, "SALINAS CAMPO #-940");
        lotes = (Lotes) getEntityManager().save(lotes);

        //Log.e("Lotes: ", "Lotes: "+lotes.getColumnValueList().getAsString(Lotes.ID_LOTE));

        Frentes fte = new Frentes().entityConfig();
        fte.setValue("descripcion", "Frente Manual");
        getEntityManager().save(fte);

        Empleados emp = new Empleados().entityConfig();
        emp.setValue(Empleados.EMPRESA,"30");
        emp.setValue("nombre_puesto", "Conductor Cabezal");
        emp.setValue("nombre", "Juan De los Santos");
        emp.setValue("estado", "ACTIVO");
        getEntityManager().save(emp);*/

        Rangos rangos = new Rangos().entityConfig();
        rangos.setValue(Rangos.EMPRESA,"30");
        rangos.setValue(Rangos.PERIODO,"19");
        rangos.setValue(Rangos.DISPOSITIVO, "Dispositivo 1");
        rangos.setValue(Rangos.ENVIO_DESDE,"1");
        rangos.setValue(Rangos.ENVIO_HASTA,"10");
        rangos.setValue(Rangos.ENVIO_ACTUAL,"1");
        rangos.setValue(Rangos.TICKET_DESDE,"1");
        rangos.setValue(Rangos.TICKET_HASTA,"10");
        rangos.setValue(Rangos.TICKET_ACTUAL,"1");
        rangos.setValue(Rangos.STATUS, "ACTIVO");
        getEntityManager().save(rangos);

        /*Log.e("Valor","Valor Correlativo: "+rangos.getColumnValueList().getAsString(Rangos.CORRELATIVO));

        rangos = (Rangos) getEntityManager().findOnce(Rangos.class, "MAX(" + Rangos.ENVIO_ACTUAL + ")+1 "+Rangos.ENVIO_ACTUAL,"dispositivo = 'Dispositivo 1'", null);
        Log.e("Valor","Valor Envio: "+rangos.getColumnValueList().getAsString(Rangos.ENVIO_ACTUAL));*/
    }
}
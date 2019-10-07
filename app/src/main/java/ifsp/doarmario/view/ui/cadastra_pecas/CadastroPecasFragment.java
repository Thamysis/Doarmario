package ifsp.doarmario.view.ui.cadastra_pecas;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.CategoriaDAO;
import ifsp.doarmario.model.dao.CorDAO;
import ifsp.doarmario.model.dao.MarcadorDAO;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Categoria;
import ifsp.doarmario.model.vo.Cor;
import ifsp.doarmario.model.vo.Marcador;
import ifsp.doarmario.model.vo.Vestuario;

public class CadastroPecasFragment extends Fragment {
    private ImageButton btt_imagem_vestuario;
    private String url_imagem;
    private EditText edit_descricao_vestuario;
    private Spinner spn_cor;
    private Spinner spn_categoia;
    private Spinner spn_marcador;
    private Button btt_adicionar;
    private String status_vestuario;
    private AlertDialog picker;
    private static final int IMAGE_GALLERY_REQUEST = 1;
    private static final int PERMISSAO_REQUEST = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 3;
    static String mCurrentPhotoPath;
    private String nomeUsuarioAtual;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_pecas, container, false);

        //recuperar nome do usuário atual
        nomeUsuarioAtual = (String) getActivity().getIntent().getSerializableExtra("usuario");

        final VestuarioDAO vestuarioDAO = new VestuarioDAO(getActivity().getApplicationContext());
        btt_imagem_vestuario = view.findViewById(R.id.btt_seleciona_img_vestuario);
        edit_descricao_vestuario = view.findViewById(R.id.descricao_vestuario);
        btt_adicionar = view.findViewById(R.id.bttAdicionar);
        status_vestuario = "n";

        spn_cor = (Spinner) view.findViewById(R.id.spnCor);
        spn_categoia = (Spinner) view.findViewById(R.id.spnCategoria);
        spn_marcador = (Spinner) view.findViewById(R.id.spnMarcadores);

        CorDAO corDAO = new CorDAO(getActivity());
        CategoriaDAO categoriaDAO = new CategoriaDAO(getActivity());
        MarcadorDAO marcadorDAO = new MarcadorDAO(getActivity());

        ArrayList<Cor> corLista = corDAO.listar();
        ArrayList<Categoria> categoriaLista = categoriaDAO.listar();
        ArrayList<Marcador> marcadorLista = marcadorDAO.listar();

        ArrayAdapter<Cor> corAdapter = new ArrayAdapter<Cor>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, corLista);
        spn_cor.setAdapter(corAdapter);

        ArrayAdapter<Categoria> categoriaAdapter = new ArrayAdapter <Categoria>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, categoriaLista);
        spn_categoia.setAdapter(categoriaAdapter);

        ArrayAdapter<Marcador> marcadorAdapter = new ArrayAdapter<Marcador>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, marcadorLista);
        spn_marcador.setAdapter(marcadorAdapter);

        ArrayList<String> itens = new ArrayList<String>();
        itens.add("Câmera");
        itens.add("Galeria");
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_picker, itens);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Selecione como deseja escolher a imagem.");

        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                picker.dismiss();
                if(arg1 == 1){
                    getPermisssionsGaleria();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    String[] mimeTypes = {"image/jpeg", "image/png"};
                    intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                    startActivityForResult(intent,IMAGE_GALLERY_REQUEST);
                }else{
                    getPermissionsCamera();
                }

            }
        });
        picker = builder.create();

        btt_imagem_vestuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show();
            }

        });

        btt_adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descricao_vestuario = edit_descricao_vestuario.getText().toString();
                Cor cor = (Cor) spn_cor.getSelectedItem();
                Categoria categoria = (Categoria) spn_categoia.getSelectedItem();
                Marcador marcador = (Marcador) spn_marcador.getSelectedItem();

                if ((!descricao_vestuario.isEmpty()) && (url_imagem != null) ) {
                    Vestuario vestuario = new Vestuario(descricao_vestuario,url_imagem, status_vestuario,
                            cor.getId_cor(), categoria.getId_categoria(), marcador.getId_marcador(), nomeUsuarioAtual);
                    if (vestuarioDAO.salvar(vestuario)) {
                        //nota: fechar fragmento e voltar pra págiina inicial

                        Toast.makeText(getActivity().getApplicationContext(), "Sucesso ao salvar Vestuário!",
                                Toast.LENGTH_SHORT).show();
                        //CadastroPecasFragment.this.onStop();//nota: o que isso faz?
                        onDestroyView();

                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(), "Erro ao salvar Vestuário!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return view;
    }

    public void getPermisssionsGaleria(){

        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)!= (PackageManager.PERMISSION_GRANTED)){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(getActivity(), new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, PERMISSAO_REQUEST);
            }
        }

    }
    private void getPermissionsCamera() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                }, REQUEST_IMAGE_CAPTURE);
            }
        }else{
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                photoFile = File.createTempFile("PHOTOAPP", ".jpg", storageDir);
                mCurrentPhotoPath = "file:" + photoFile.getAbsolutePath();
                url_imagem = photoFile.getAbsolutePath();
            }
            catch(IOException ex){
                Toast.makeText(getActivity().getApplicationContext(), "Erro ao tirar a foto", Toast.LENGTH_SHORT).show();

            }

            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), "camera.fileprovider", photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case IMAGE_GALLERY_REQUEST:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    url_imagem = cursor.getString(columnIndex);

                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    btt_imagem_vestuario.setImageBitmap(BitmapFactory.decodeFile(url_imagem));
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    try {
                        //url_imagem = String.valueOf(getContentResolver().openInputStream(Uri.parse(mCurrentPhotoPath)));

                        Bitmap bm1 = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(Uri.parse(mCurrentPhotoPath)));
                        btt_imagem_vestuario.setImageBitmap(bm1);
                    } catch (FileNotFoundException fnex) {
                        Toast.makeText(getActivity().getApplicationContext(), "Foto não encontrada!", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permisssions[], int [] grantResults){
        if(requestCode == PERMISSAO_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }else{
                //btt_imagem_vestuario.setEnabled(false);
            }
            return;
        }else if (requestCode == REQUEST_IMAGE_CAPTURE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();

            } else {
                Toast.makeText(getActivity(), "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
            }
            return;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
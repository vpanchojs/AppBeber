package com.jcode.tebocydelevery.MyProfile.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.main.ui.MainActivity;
import com.jcode.tebocydelevery.models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class MyProfileFragment extends Fragment {

    private static final String TAG = "MyProfile";

    private static final int REQUEST_GET_IMAGE = 100;
    private static final int REQUEST_PICTURE = 1;
    private static String photo = "";
    private final int MY_PERMISSIONS_REQUEST_CODE = 1;
    @BindView(R.id.civ_photo_user)
    CircleImageView civPhotoUser;
    @BindView(R.id.et_name)
    TextInputEditText etName;
    @BindView(R.id.et_lastname)
    TextInputEditText etLastname;
    @BindView(R.id.et_dni)
    TextInputEditText etDni;
    @BindView(R.id.et_phone)
    TextInputEditText etPhone;
    @BindView(R.id.et_birthdate)
    TextInputEditText etBirthdate;
    @BindView(R.id.et_email)
    TextInputEditText etEmail;
    @BindView(R.id.btn_update_user)
    Button btnUpdateUser;
    Unbinder unbinder;
    @BindView(R.id.ib_birhdate)
    ImageButton ibBirhdate;
    @BindView(R.id.ll_info_user)
    LinearLayout llInfoUser;
    @BindView(R.id.icon_email)
    ImageView iconEmail;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.container_login)
    CoordinatorLayout containerLogin;
    @BindView(R.id.fab_photo_user)
    FloatingActionButton fabPhotoUser;
    private DatePickerDialog fromDatePickerDialog;
    private Long birthdate;
    private OnMyProfileInteractionListener listener;
    private User me;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    public static MyProfileFragment newInstance(User me) {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("me", me);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            me = getArguments().getParcelable("me");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        updateDataUser();
        etBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });

        etDni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etDni.getText().length() == 10) {
                    validateDni();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    public boolean validateDni() {
        if (User.validaDni(etDni.getText().toString())) {
            etDni.setError(null);
            return true;
        } else {
            //etDni.setError(getString(R.string.login_error_message_dni_incorrect));
            etDni.setError("Cédula incorrecta");
            return false;
        }
    }

    public boolean validatePhone() {
        if (etPhone.getText().toString().length() == 10) {
            etPhone.setError(null);
            return true;
        } else {
            etPhone.setError("Número incorrecto");
            return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        checkPermissions();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.fab_photo_user, R.id.btn_update_user, R.id.ib_birhdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_birhdate:
                showDatePicker();
                break;
            case R.id.fab_photo_user:
                if (checkPermissions()) {
                    getPhoto();
                }
                break;
            case R.id.btn_update_user:
                if (validarUser()) {
                    me.setEmail(etEmail.getText().toString());
                    me.setName(etName.getText().toString());
                    me.setLastname(etLastname.getText().toString());
                    me.setBirthdate(etBirthdate.getText().toString());
                    me.setPhone(etPhone.getText().toString());
                    me.setDni(etDni.getText().toString());
                    listener.updateUser();
                    Log.e(TAG, "User validado correctamente");
                } else {
                    Toast.makeText(getContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public boolean validarUser() {
        return validarEdad() && validateDni() && validatePhone();
    }

    /*Codigo fotos*/
    private File getFile() {
        File photoFile = null;
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            photoFile = File.createTempFile(imageFileName, ".jpg", storageDir);
            photo = photoFile.getAbsolutePath();
            Log.e(TAG, "obtener foto");
        } catch (IOException e) {
            //showSnackbar(R.string.main_error_dispatch_camera);
            Log.e("crear archivo", e.getMessage().toString());
        }

        return photoFile;
    }

    public void getPhoto() {
        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList<>();
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("return-data", true);

        File photoFile = getFile();
        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                intentList = addIntentsToList(intentList, cameraIntent);
            }
        }

        if (pickIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            intentList = addIntentsToList(intentList, pickIntent);
        }

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    getString(R.string.main_message_picture_source));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        if (chooserIntent != null) {
            startActivityForResult(chooserIntent, REQUEST_GET_IMAGE);
        }
    }

    public void showDatePicker() {
        Calendar c = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                etBirthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                validarEdad();
            }

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.show();

    }


    public boolean validarEdad() {
        if (User.calcularEdad(etBirthdate.getText().toString()) >= 18) {
            etBirthdate.setError(null);
            return true;
        } else {
            etBirthdate.setError("Debe ser mayor de edad");
            return false;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "asdasd");
        if (requestCode == REQUEST_GET_IMAGE) {
            if (resultCode == RESULT_OK) {
                boolean fromCamera = (data == null || data.getData() == null);
                if (fromCamera) {
                    addToGallery();
                } else {
                    photo = getRealPathFromURI(data.getData());
                }

                civPhotoUser.setImageURI(Uri.parse(photo));
                listener.updatePhotoUser(photo);
            }
        }
    }


    private List<Intent> addIntentsToList(List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetIntent = new Intent(intent);
            targetIntent.setPackage(packageName);
            list.add(targetIntent);
        }

        return list;
    }

    private void addToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(photo);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = null;

        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            if (contentURI.toString().contains("mediaKey")) {
                cursor.close();

                try {
                    File file = File.createTempFile("tempImg", ".jpg", getActivity().getCacheDir());
                    InputStream input = getActivity().getContentResolver().openInputStream(contentURI);
                    OutputStream output = new FileOutputStream(file);

                    try {
                        byte[] buffer = new byte[4 * 1024];
                        int read;

                        while ((read = input.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                        result = file.getAbsolutePath();
                    } finally {
                        output.close();
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                cursor.moveToFirst();
                int dataColumn = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(dataColumn);
                cursor.close();
            }
        }
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyProfileInteractionListener) {
            listener = (OnMyProfileInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void updateDataUser() {
        if (me != null) {
            if (me.getPhone() != null) {
                etPhone.setText(me.getPhone());
            }

            if (me.getBirthdate() != null) {
                etBirthdate.setText(me.getBirthdate());
            }

            if (me.getDni() != null) {
                etDni.setText(me.getDni());
            }

            etName.setText(me.getName());
            etLastname.setText(me.getLastname());
            etEmail.setText(me.getEmail());
            MainActivity.imageLoader.get(me.getUrl_photo(), ImageLoader.getImageListener(civPhotoUser, R.drawable.ic_person, R.drawable.ic_cancel));
        } else {
            ((MainActivity) getActivity()).showProgressBar(true);
        }
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE) {
            return;
        }
        boolean isGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }
    }

    public interface OnMyProfileInteractionListener {
        void updateUser();
        void updatePhotoUser(String photo);
    }
}

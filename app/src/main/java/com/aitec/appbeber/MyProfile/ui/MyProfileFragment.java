package com.aitec.appbeber.MyProfile.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aitec.appbeber.MyApplication;
import com.aitec.appbeber.MyProfile.ProfilePresenter;
import com.aitec.appbeber.R;
import com.aitec.appbeber.RackProducts.ui.DialogMessageFragment;
import com.aitec.appbeber.init.login.ui.LoginActivity;
import com.aitec.appbeber.main.ui.MainActivity;
import com.aitec.appbeber.models.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class MyProfileFragment extends Fragment implements ProfileView {

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
    Unbinder unbinder;
    @BindView(R.id.ib_birhdate)
    ImageButton ibBirhdate;
    @BindView(R.id.icon_email)
    ImageView iconEmail;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.fab_photo_user)
    FloatingActionButton fabPhotoUser;
    private ProgressDialog progressDialog;

    MyApplication application;
    @Inject
    ProfilePresenter presenter;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private DatePickerDialog fromDatePickerDialog;
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
        setHasOptionsMenu(true);
        application = (MyApplication) getActivity().getApplication();
        setupInjection();
    }


    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private void setupInjection() {
        application.getProfileComponent(this).inject(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_singout:
                presenter.singOut();
                break;
            case R.id.action_update_user:
                if (validateForm(etDni) && validateForm(etName) && validateForm(etLastname) && validateForm(etPhone) && validateForm(etBirthdate)) {
                    if (validarUser()) {
                        me.setEmail(etEmail.getText().toString());
                        me.setName(etName.getText().toString());
                        me.setLastname(etLastname.getText().toString());
                        me.setBirthdate(etBirthdate.getText().toString());
                        me.setPhone(etPhone.getText().toString());
                        me.setDni(etDni.getText().toString());
                        presenter.updateUser(me);
                        Log.e(TAG, "User validado correctamente");
                    } else {
                        Toast.makeText(getContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.subscribeMyProfile();
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
            //  etDni.setError(getString(R.string.login_error_message_dni_incorrect));
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
        presenter.onStart();
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


    @OnClick({R.id.fab_photo_user, R.id.ib_birhdate})
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
        }
    }

    public boolean validateForm(TextInputEditText tie) {
        if (tie.getText().toString().isEmpty()) {
            tie.setError("Campo Vacio");
            return false;
        } else {
            tie.setError(null);
            return true;
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
                presenter.updatePhotoUser(photo);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
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

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
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


    @Override
    public void showProgressBar(Boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void navigationLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setUserData(User user) {
        me = user;
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

        Glide.with(getContext())
                .load(me.getUrl_photo())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .override(600, 400)
                .into(civPhotoUser);
    }

    @Override
    public void navigationToBuyCar() {
        ((MainActivity) getActivity()).navigationToBuyCar();
    }

    @Override
    public void dialogMessage(String title, String message) {
        DialogMessageFragment dialogMessageFragment = DialogMessageFragment.newInstance(message, title);
        dialogMessageFragment.show(getChildFragmentManager(), "DialogMessage");
    }

    @Override
    public void setPhotoUrl() {
        civPhotoUser.setImageURI(Uri.parse(photo));
    }

    @Override
    public void showProgressDialog(Boolean show, String message) {
        if (show) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }
}

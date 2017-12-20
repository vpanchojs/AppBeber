package com.jcode.tebocydelevery.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class User implements Parcelable {
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel pc) {
            return new User(pc);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private String id_user;
    private String name;
    private String lastname;
    private String phone;
    private String dni;
    private String birthdate;
    private String email;
    private String url_photo;

    public User() {

    }

    public User(String id_user, String name, String lastname, String phone, String dni, String birthdate, String email, String url_photo) {
        this.id_user = id_user;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.dni = dni;
        this.birthdate = birthdate;
        this.email = email;
        this.url_photo = url_photo;
    }

    public User(Parcel in) {
        this.id_user = in.readString();
        this.name = in.readString();
        this.lastname = in.readString();
        this.phone = in.readString();
        this.dni = in.readString();
        this.birthdate = in.readString();
        this.email = in.readString();
        this.url_photo = in.readString();
    }

    public static boolean validaDni(String x) {
        int suma = 0;
        int a[] = new int[x.length() / 2];
        int b[] = new int[(x.length() / 2)];
        int c = 0;
        int d = 1;
        for (int i = 0; i < x.length() / 2; i++) {
            a[i] = Integer.parseInt(String.valueOf(x.charAt(c)));
            c = c + 2;
            if (i < (x.length() / 2) - 1) {
                b[i] = Integer.parseInt(String.valueOf(x.charAt(d)));
                d = d + 2;
            }
        }

        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] * 2;
            if (a[i] > 9) {
                a[i] = a[i] - 9;
            }
            suma = suma + a[i] + b[i];
        }
        int aux = suma / 10;
        int dec = (aux + 1) * 10;
        if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length() - 1))))
            return true;
        else return suma % 10 == 0 && x.charAt(x.length() - 1) == '0';
    }
    // [END post_to_map]

    public static int calcularEdad(String fecha) {
        Log.e("User", fecha);
        String datetext = fecha;
        try {
            Calendar birth = new GregorianCalendar();
            Calendar today = new GregorianCalendar();
            int age = 0;
            int factor = 0;
            Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(datetext);
            Date currentDate = new Date(); //current date
            birth.setTime(birthDate);
            today.setTime(currentDate);
            if (today.get(Calendar.MONTH) <= birth.get(Calendar.MONTH)) {
                if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)) {
                    if (today.get(Calendar.DATE) > birth.get(Calendar.DATE)) {
                        factor = -1; //Aun no celebra su cumpleaÃ±os
                    }
                } else {
                    factor = -1; //Aun no celebra su cumpleaÃ±os
                }
            }

            age = (today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)) + factor;
            return age;
        } catch (ParseException e) {
            Log.e("User", e.toString());
            return -1;
        }

    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }

    @Exclude
    public Map<String, Object> toMapPost() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("lastname", lastname);
        result.put("dni", dni);
        result.put("phone", phone);
        result.put("birthdate", birthdate);
        result.put("url_photo", url_photo);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapUserOrder() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("lastname", lastname);
        result.put("id_user", id_user);
        return result;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_user);
        dest.writeString(name);
        dest.writeString(lastname);
        dest.writeString(phone);
        dest.writeString(dni);
        dest.writeString(birthdate);
        dest.writeString(email);
        dest.writeString(url_photo);
    }


}


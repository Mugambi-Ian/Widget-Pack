package nenecorp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class PasswordField extends LinearLayout {
    private static final float DEF_VAL = -20;
    private View btnSwitch;
    private Drawable openDrawable, closedDrawable, containerDrawable;
    private EditText password;
    private ImageView imageView;
    private boolean hidden = true;

    public void clearText() {
        password.setText("");
    }

    public void setError(String errorMessage) {
        password.setError(errorMessage);
    }

    public enum FieldType {
        BORDERED(1),
        MATERIAL(2),
        CUSTOM(3);
        private final int id;

        FieldType(int id) {
            this.id = id;
        }

        private static FieldType getType(int i) {
            switch (i) {
                case 1:
                    return BORDERED;
                case 2:
                    return MATERIAL;
                case 3:
                    return CUSTOM;
            }
            return null;
        }

        private int getValue() {
            return id;
        }
    }

    public PasswordField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getPasswordType(attrs);
        loadDrawables(attrs);
    }

    private void loadDrawables(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PasswordField);
        closedDrawable = a.getDrawable(R.styleable.PasswordField_closedDrawable);
        openDrawable = a.getDrawable(R.styleable.PasswordField_openDrawable);
        containerDrawable = a.getDrawable(R.styleable.PasswordField_closedDrawable);
    }

    private void getPasswordType(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PasswordField);
        int type = array.getInt(R.styleable.PasswordField_fieldType, FieldType.BORDERED.getValue());
        array.recycle();
        setFieldType(FieldType.getType(type));
    }

    private void setFieldType(FieldType viewFieldType) {
        if (viewFieldType.getValue() == FieldType.BORDERED.getValue()) {
            borderedPasswordField();
        } else if (viewFieldType.getValue() == FieldType.MATERIAL.getValue()) {
            materialPasswordField();
        } else if (viewFieldType.getValue() == FieldType.CUSTOM.getValue()) {
            customPasswordField();
        }
    }

    private void materialPasswordField() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_password_material, this, true);
        btnSwitch = findViewById(R.id.VPS_btnSwitch);
        password = findViewById(R.id.VPS_edPassword);
        imageView = findViewById(R.id.VPS_drawable);
        if (closedDrawable == null) {
            closedDrawable = getContext().getDrawable(R.drawable.dr_closed);
        }
        if (openDrawable == null) {
            openDrawable = getContext().getDrawable(R.drawable.dr_opened);
        }
        initView();
    }

    private void borderedPasswordField() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_password_bordererd, this, true);
        btnSwitch = findViewById(R.id.VPS_btnSwitch);
        password = findViewById(R.id.VPS_edPassword);
        imageView = findViewById(R.id.VPS_drawable);
        if (closedDrawable == null) {
            closedDrawable = getContext().getDrawable(R.drawable.dr_closed_w);
        }
        if (openDrawable == null) {
            openDrawable = getContext().getDrawable(R.drawable.dr_opened_w);
        }
        initView();
    }

    private void customPasswordField() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_password_custom, this, true);
        btnSwitch = findViewById(R.id.VPS_btnSwitch);
        password = findViewById(R.id.VPS_edPassword);
        imageView = findViewById(R.id.VPS_drawable);
        View container = findViewById(R.id.VPS_container);
        if (closedDrawable == null) {
            closedDrawable = getContext().getDrawable(R.drawable.dr_closed);
        }
        if (openDrawable == null) {
            openDrawable = getContext().getDrawable(R.drawable.dr_opened);
        }
        if (containerDrawable == null) {
            containerDrawable = getContext().getDrawable(R.drawable.dr_empty);
        }
        container.setBackground(containerDrawable);
        initView();
    }

    private void initView() {
        this.refreshDrawableState();
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        imageView.setBackground(openDrawable);
        btnSwitch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hidden) {
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    imageView.setBackground(closedDrawable);
                    hidden = false;
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imageView.setBackground(openDrawable);
                    hidden = true;
                }
            }
        });
        password.setHint("Password");
    }

    protected String getPassword() {
        return password.getText().toString();
    }

    public String getEncryptedPassword(String encryptionKey) {
        return PasswordCipher.encrypt(getPassword(), encryptionKey);

    }

    public boolean isPasswordValid(String encryptionKey, String encryptedPassword) {
        String passwordS = PasswordCipher.decrypt(encryptedPassword, encryptionKey);
        return passwordS.equals(getPassword());
    }

    private static class PasswordCipher {
        private final static String AES = "AES";

        static String encrypt(String password, String encryptionKey) {
            SecretKeySpec key;
            try {
                key = generateKey(encryptionKey);
                Cipher cipher = Cipher.getInstance(AES);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] encrypted = cipher.doFinal(password.getBytes());
                return Base64.encodeToString(encrypted, Base64.DEFAULT);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
            return null;
        }

        static SecretKeySpec generateKey(String encryptionKey) throws NoSuchAlgorithmException {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
            digest.update(bytes, 0, bytes.length);
            byte[] key = digest.digest();
            SecretKeySpec spec = new SecretKeySpec(key, "AES");
            return spec;
        }

        static String decrypt(String output, String encryptionKey) {
            SecretKeySpec key = null;
            try {
                key = generateKey(encryptionKey);
                Cipher c = Cipher.getInstance(AES);
                c.init(Cipher.DECRYPT_MODE, key);
                byte[] decodedValue = Base64.decode(output, Base64.DEFAULT);
                byte[] decValue = c.doFinal(decodedValue);
                return new String(decValue);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

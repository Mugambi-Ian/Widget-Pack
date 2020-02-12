package nenecorp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import static android.content.ContentValues.TAG;

public class PinIndicator extends LinearLayout {
    private static final float DEF_VAL = -20;
    private Drawable pinDrawable, backgroundDrawable;
    private int pinLength;
    private String inputPin = "", cachedPin = "";
    private RecyclerView pinItems;
    private int itemHeight;
    private int itemWidth;
    private int itemMargin;
    private int itemElevation;
    private String encryptionKey;
    private PinAttemptResult attemptResult;
    private String encryptedPassword;
    private ArrayList<PinItem> pinItemsList;
    private IChannel iChannel;


    private enum InputChanel {
        NUMPAD(1), EDIT_TEXT(2);

        InputChanel(int i) {
        }
    }

    private class IChannel {
        private NumPad numPad;
        private EditText editText;
        private InputChanel inputChanel;


        public IChannel(EditText editText) {
            this.editText = editText;
            inputChanel = InputChanel.EDIT_TEXT;
        }

        public IChannel(NumPad numPad) {
            this.numPad = numPad;
            inputChanel = InputChanel.NUMPAD;
        }

        void clear() {
            switch (inputChanel) {
                case NUMPAD:
                    numPad.clear();
                    break;
                case EDIT_TEXT:
                    editText.setText("");
                    break;
            }
        }


    }

    public interface PinAttemptResult {
        void verifiedSuccessFully();

        void verificationFailed();
    }

    public PinIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadAttrs(attrs);
    }

    private void loadAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PinIndicator);
        pinDrawable = a.getDrawable(R.styleable.PinIndicator_piPinDrawable);
        backgroundDrawable = a.getDrawable(R.styleable.PinIndicator_piBackground);
        if (pinDrawable == null) {
            pinDrawable = getContext().getDrawable(R.drawable.dr_asterick);
        }
        itemHeight = (int) a.getDimension(R.styleable.PinIndicator_piItemHeight, DEF_VAL);
        itemWidth = (int) a.getDimension(R.styleable.PinIndicator_piItemWidth, DEF_VAL);
        itemMargin = (int) a.getDimension(R.styleable.PinIndicator_piItemMargin, DEF_VAL);
        itemElevation = (int) a.getDimension(R.styleable.PinIndicator_piItemElevation, DEF_VAL);
        pinLength = a.getInteger(R.styleable.PinIndicator_piPinLength, 4);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_pin_indicator, this);
        pinItems = findViewById(R.id.VPI_pinItems);
        pinItemsList = getPinItems(pinLength);
        PinAdapter pinAdapter = new PinAdapter(getContext(), pinItemsList, AnimationType.NONE);
        pinItems.setAdapter(pinAdapter);
        pinItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }


    public void reallyCoolNumpad(NumPad numPad) {
        iChannel = new IChannel(numPad);
        numPad.setInputListener(new NumPad.InputListener() {
            @Override
            public void onInputChanged(String number) {
                inputPin = number;
                inputChanged();
            }
        });
    }


    public void useAndroidKeyboard() {
        EditText editText = findViewById(R.id.PI_editText);
        editText.setVisibility(VISIBLE);
        editText.requestFocus();
        iChannel = new IChannel(editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    inputPin = "" + Integer.parseInt(s.toString());
                    inputChanged();
                } catch (Exception e) {
                    Log.e(TAG, "Input Channel: string " + s + " contains non-numerical values.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void inputChanged() {
        if (cachedPin.length() < pinLength) {
            if (inputPin.length() > cachedPin.length()) {
                addPinChar();
                cachedPin = inputPin;
            } else {
                removeLastChar();
                cachedPin = inputPin;
            }
        } else if (cachedPin.length() == pinLength) {
            if (inputPin.length() > cachedPin.length()) {
                addPinChar();
                cachedPin = inputPin;
            } else {
                removeLastChar();
                cachedPin = inputPin;
            }
            if (encryptionKey != null && attemptResult != null) {
                if (isPasswordValid(encryptionKey, encryptedPassword)) {
                    attemptResult.verifiedSuccessFully();
                } else {
                    attemptResult.verificationFailed();
                }
            }
        } else {
            clearPinCache();
        }
    }

    private void clearPinCache() {
        ArrayList<PinItem> x = pinItemsList;
        for (PinItem pinItem : x) {
            pinItem.recordEmptyValue();
        }
        pinItemsList = x;
        PinAdapter pinAdapter = new PinAdapter(getContext(), pinItemsList, AnimationType.REMOVE_ALL_CHAR);
        pinItems.setAdapter(pinAdapter);
        pinItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        inputPin = "";
        cachedPin = "";
        iChannel.clear();
    }

    private void removeLastChar() {
        int lastChar = inputPin.length();
        ArrayList<PinItem> s = getPinItems(pinLength);
        for (int z = 0; z < inputPin.length(); z++) {
            if (z == lastChar) {
                s.get(z - 1).recordNewValue();
                s.get(z).recordEmptyValue();
            } else {
                s.get(z).recordStoredValue();
            }

        }
        pinItemsList = s;
        PinAdapter pinAdapter = new PinAdapter(getContext(), pinItemsList, AnimationType.REMOVE_LAST_CHAR);
        pinItems.setAdapter(pinAdapter);
        pinItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    private void addPinChar() {
        int lastChar = inputPin.length();
        ArrayList<PinItem> s = getPinItems(pinLength);
        for (int z = 0; z < inputPin.length(); z++) {
            if (z == lastChar) {
                s.get(z).recordStoredValue();
                s.get(z + 1).recordNewValue();
            } else if (z < pinLength) {
                s.get(z).recordStoredValue();
            }
        }
        pinItemsList = s;
        PinAdapter pinAdapter = new PinAdapter(getContext(), pinItemsList, AnimationType.REMOVE_LAST_CHAR);
        pinItems.setAdapter(pinAdapter);
        pinItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    private String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length()));
    }

    private enum AnimationType {
        ADD_LAST_CHAR(1), REMOVE_LAST_CHAR(2), REMOVE_ALL_CHAR(3), NONE(3);

        AnimationType(int i) {
        }
    }

    private class PinAdapter extends RecyclerView.Adapter<PinAdapter.Holder> {
        private LayoutInflater inflater;
        private ArrayList<PinItem> pinItems;
        private AnimationType animationType;


        PinAdapter(Context context, ArrayList<PinItem> pinItems, AnimationType animationType) {
            this.inflater = LayoutInflater.from(context);
            this.pinItems = pinItems;
            this.animationType = animationType;
        }


        @Override
        public PinAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.pin_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(PinAdapter.Holder holder, final int position) {
            View pinView = holder.parentView.findViewById(R.id.PI_pinBackground);
            ImageView pdImageView = pinView.findViewById(R.id.PI_pinDrawable);
            PinItem pinItem = pinItems.get(position);
            LayoutParams params = (LayoutParams) pinView.getLayoutParams();
            if (pinItem.itemHeight != DEF_VAL) {
                params.height = pinItem.itemHeight;
            }
            if (pinItem.itemWidth != DEF_VAL) {
                params.width = pinItem.itemWidth;
            }
            if (pinItem.itemMargin != DEF_VAL) {
                params.setMarginStart(pinItem.itemMargin);
                params.setMarginEnd(pinItem.itemMargin);
            }
            pinView.setBackground(pinItem.backgroundDrawable);
            pdImageView.setBackground(pinItem.pinDrawable);
            pinView.setLayoutParams(params);
            if (pinItem.itemElevation != DEF_VAL) {
                ViewCompat.setElevation(pinView, pinItem.itemElevation);
            }
            pdImageView.setVisibility(GONE);
            if (pinItem.pinItemState == State.STORED) {
                pdImageView.setVisibility(VISIBLE);
            }
            if (animationType == AnimationType.REMOVE_LAST_CHAR) {
                if (pinItem.pinItemState == State.NEW) {
                    AnimateWidget.fadeOut(pdImageView, 200, new AnimationEventListener() {
                        @Override
                        public void onComplete(View x) {
                            x.setVisibility(GONE);
                            updateNewLastChar(position - 1);
                        }
                    });
                }
            }
            if (animationType == AnimationType.REMOVE_ALL_CHAR) {
                AnimateWidget.fadeOut(pdImageView, 200, new AnimationEventListener() {
                    @Override
                    public void onComplete(View x) {
                        x.setVisibility(GONE);
                    }
                });
            }
            if (animationType == AnimationType.ADD_LAST_CHAR) {
                if (pinItem.pinItemState == State.NEW) {
                    AnimateWidget.fadeIn(pdImageView, 200, new AnimationEventListener() {
                        @Override
                        public void onComplete(View x) {
                            x.setVisibility(VISIBLE);
                        }
                    });
                }
            }

        }


        @Override
        public int getItemCount() {
            return pinItems.size();
        }


        private class Holder extends RecyclerView.ViewHolder {
            View parentView;

            Holder(View itemView) {
                super(itemView);
                parentView = itemView.findViewById(R.id.PI_pinParentView);
            }

        }
    }

    private void updateNewLastChar(int i) {
    }

    public void displayPin(String pin) {
        if (pin.length() <= pinLength) {
            for (int i = 0; i < pin.length(); i++) {
                View x = pinItems.getChildAt(i - 1);
                View id = x.findViewById(R.id.PI_pinDrawable);
                AnimateWidget.fadeIn(id, 200, new AnimationEventListener() {
                    @Override
                    public void onComplete(View x) {
                        x.setVisibility(VISIBLE);
                    }
                });
            }
        }
    }

    public void setupVerification(String encryptionKey, String encryptedPassword, PinAttemptResult attemptResult) {
        this.encryptionKey = encryptionKey;
        this.encryptedPassword = encryptedPassword;
        this.attemptResult = attemptResult;
    }

    protected String getPassword() {
        return inputPin;
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
                String returnValue = Base64.encodeToString(encrypted, Base64.DEFAULT);
                return returnValue;
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
                String decryptedValue = new String(decValue);
                return decryptedValue;
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private ArrayList<PinItem> getPinItems(int pinLength) {
        ArrayList<PinItem> pinItems = new ArrayList<>();
        for (int i = 0; i < pinLength; i++) {
            pinItems.add(new PinItem());
        }
        return pinItems;
    }

    private enum State {
        EMPTY(1), NEW(2), STORED(3);

        State(int i) {
        }
    }

    private class PinItem {
        private int itemHeight;
        private int itemWidth;
        private int itemMargin;
        private int itemElevation;
        private Drawable pinDrawable, backgroundDrawable;
        private State pinItemState;


        PinItem() {
            itemHeight = PinIndicator.this.itemHeight;
            itemWidth = PinIndicator.this.itemWidth;
            itemMargin = PinIndicator.this.itemMargin;
            pinDrawable = PinIndicator.this.pinDrawable;
            itemElevation = PinIndicator.this.itemElevation;
            backgroundDrawable = PinIndicator.this.backgroundDrawable;
            pinItemState = State.EMPTY;
        }

        void recordStoredValue() {
            pinItemState = State.STORED;
        }

        void recordNewValue() {
            pinItemState = State.NEW;
        }

        void recordEmptyValue() {
            pinItemState = State.EMPTY;
        }
    }
}

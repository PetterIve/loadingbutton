package petterive.android.loadingbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallPulseIndicator;

/**
 * Created by petteriversen on 24/09/2017.
 */

public class LoadingButton extends FrameLayout {

    public static boolean TEXT_ALL_CAPS = true;

    private final boolean textAllCaps;
    private String buttonText;
    private int loadingIndicatorColor, buttonTextColor, buttonDisabledTextColor, buttonColor, buttonDisabledColor;
    private Drawable drawableLeft, drawableTop, drawableRight, drawableBottom;
    private boolean enabled;
    private Context context;
    private Button button;
    private View loadingView;
    private float textSize;
    private boolean isLoading;
    private boolean disabled;
    private OnClickListener onclickListener;

    public LoadingButton(Context context){
        this(context, null);
    }

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LoadingButton,
                0, 0);
        try {
            buttonText = a.getString(R.styleable.LoadingButton_text);
            buttonTextColor = a.getColor(R.styleable.LoadingButton_textColor, context.getResources().getColor(R.color.colorAccent));
            buttonDisabledTextColor = a.getColor(R.styleable.LoadingButton_textColorDisabled, context.getResources().getColor(R.color.colorAccent));
            buttonColor = a.getColor(R.styleable.LoadingButton_buttonColor, context.getResources().getColor(R.color.white));
            buttonDisabledColor = a.getColor(R.styleable.LoadingButton_buttonDisabledColor, context.getResources().getColor(R.color.grey));
            loadingIndicatorColor = a.getColor(R.styleable.LoadingButton_loadingIndicatorColor, context.getResources().getColor(R.color.colorAccent));
            enabled = a.getBoolean(R.styleable.LoadingButton_enabled, true);
            drawableLeft = a.getDrawable(R.styleable.LoadingButton_drawableLeft);
            drawableTop = a.getDrawable(R.styleable.LoadingButton_drawableTop);
            drawableRight = a.getDrawable(R.styleable.LoadingButton_drawableRight);
            drawableBottom = a.getDrawable(R.styleable.LoadingButton_drawableBottom);
            textSize = a.getDimension(R.styleable.LoadingButton_textSize, 0);
            textAllCaps = a.getBoolean(R.styleable.LoadingButton_textAllCaps, TEXT_ALL_CAPS);
        } finally {
            a.recycle();
        }
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        FrameLayout view = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.view_loading_button, this, true);
        button = view.findViewById(R.id.button);

        AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context, null, R.style.AVLoadingIndicatorView_Small);
        avLoadingIndicatorView.setIndicator(new BallPulseIndicator());
        avLoadingIndicatorView.setIndicatorColor(loadingIndicatorColor);
        LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        avLoadingIndicatorView.setLayoutParams(lp);
        loadingView = avLoadingIndicatorView;

        view.addView(loadingView);
        loadingView.setVisibility(View.GONE);

        button.setTextColor(buttonTextColor);
        button.setText(buttonText);
        if(!textAllCaps) {
            button.setTransformationMethod(null);
        }
        button.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        if(textSize > 0) {
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        button.setOnClickListener(onclickListener);
        updateButtonState();
    }

    public void startLoading(){
        button.setEnabled(false);
        button.setText("");
        loadingView.setVisibility(View.VISIBLE);
    }

    public void stopLoading(){
        button.setEnabled(true);
        button.setText(buttonText);
        loadingView.setVisibility(View.GONE);

    }

    private void updateButtonState() {
        if(enabled) {
            ViewCompat.setBackgroundTintList(button, ColorStateList.valueOf(buttonColor));
            button.setTextColor(buttonTextColor);
        }
        else {
            ViewCompat.setBackgroundTintList(button, ColorStateList.valueOf(buttonDisabledColor));
            button.setTextColor(buttonDisabledTextColor);
        }
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
        updateButtonState();
    }

    public void setOnClickListener(OnClickListener onclickListener){
        this.onclickListener = onclickListener;
        if(button == null){
            return;
        }
        button.setOnClickListener(onclickListener);
    }

    public void setDisabled(boolean disabled){
        button.setEnabled(!disabled);
    }

    public void setText(String text) {
        buttonText = text;
        button.setText(buttonText);
    }

}

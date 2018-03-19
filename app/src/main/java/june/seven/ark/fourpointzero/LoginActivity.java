package june.seven.ark.fourpointzero;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FancyButton facebookLoginBtn = new FancyButton(this);
        facebookLoginBtn.setText("Login with Facebook");
        facebookLoginBtn.setBackgroundColor(Color.parseColor("#3b5998"));
        facebookLoginBtn.setFocusBackgroundColor(Color.parseColor("#5474b8"));
        facebookLoginBtn.setTextSize(17);
        facebookLoginBtn.setRadius(5);
        facebookLoginBtn.setIconResource("\uf082");
        facebookLoginBtn.setIconPosition(FancyButton.POSITION_LEFT);
        facebookLoginBtn.setFontIconSize(30);

    }
}

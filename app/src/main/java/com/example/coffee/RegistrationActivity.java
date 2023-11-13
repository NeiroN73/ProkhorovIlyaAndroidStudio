package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    EditText username, password, rePassword;
    Button buttonSingUp;
    DBHelper myDBUser;

    Button buttonBack;

    private CheckBox agreementСheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        agreementСheckBox = findViewById(R.id.agreementСheckBox);

        //получаем вводимые значения в виде текста
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        rePassword = (EditText) findViewById(R.id.repassword);

        buttonSingUp = (Button) findViewById(R.id.buttonSignUp);

        myDBUser = new DBHelper(this);

        Button buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(view -> {

            String user = username.getText().toString();
            String pass = password.getText().toString();
            String rePass = rePassword.getText().toString();

            //если какое-то из  полей незаплнено выводим соответсвуещее значение
            if (user.equals("")||pass.equals("")||rePass.equals("")){
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            }
            //если поля заполнены
            else
            {
                //если пароль равен проверочному паролю
                if (rePass.equals(pass))
                {
                    //проверям существует ли такое юзер с таким именем уже в бд
                    Boolean userCheckResult = myDBUser.checkUsername(user);
                    if (userCheckResult == false){

                        //здесь код о нажатии на флажок
                        if (agreementСheckBox.isChecked()){
                            //добавляем юзера в бд
                            Boolean regResult = myDBUser.insertData(user, pass);

                            //успешно добавился или нет
                            if (regResult == true) {
                                Toast.makeText(this, "Вы зарегистрированы!", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(this, "Регистрация  провалилась!", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(this, "Cогласитесь с условиями!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this, "Пользователь с таким именем уже существует!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Войдите в систему!", Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {
                    Toast.makeText(this, "Пароль не совпадает!", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }
}
package com.example.instaclone

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instaclone.ui.theme.InstaCloneTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstaCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)   ,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center){
        Text(text = "Instagram", fontSize = 36.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))

        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        OutlinedTextField(value = email, onValueChange = {email=it}, label = { Text(text = "Email")}, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(36.dp))

        OutlinedTextField(
            value = password, onValueChange = {password=it},
        label = { Text(text = "Password")},
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation())
        val auth = FirebaseAuth.getInstance()
        val context = LocalContext.current
        Button(onClick = {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            task->
            if(task.isSuccessful){
                Toast.makeText(context, "Login in Success",Toast.LENGTH_SHORT).show()


            }
            else{
                Log.d("login","login failed")
                Toast.makeText(context,"Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onSignUpClick) {
            Text(text = "Don't have an account? Sign Up")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onLoginClick: () -> Unit
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "Instagram", fontSize = 36.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(30.dp))

        var username by remember{ mutableStateOf("") }
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        OutlinedTextField(value = username, onValueChange = {username=it}, label = { Text(text = "Username")}, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = {email = it}, label = { Text(text = "Email")}, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text(text = "Password")}, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(32.dp))
        val auth = FirebaseAuth.getInstance()
        val context = LocalContext.current
        Button(onClick = {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                task->
                if(task.isSuccessful){

                    Toast.makeText(context,"Sign up success",Toast.LENGTH_SHORT).show()
                 }
                else{
                    Log.d("Signup","Sign up failed")
                    Toast.makeText(context,"Sign up Failed",Toast.LENGTH_SHORT).show()}
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Sign Up")
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onLoginClick) {
            Text(text = "Already have an account? Log In")
        }
    }
}

@Composable
fun AuthScreen(){
    var showLogin by remember {
        mutableStateOf(true)
    }
    if(showLogin){
        LoginScreen(onSignUpClick = {showLogin = false})
    }
    else{
        SignUpScreen(onLoginClick = {showLogin=true})
    }
}


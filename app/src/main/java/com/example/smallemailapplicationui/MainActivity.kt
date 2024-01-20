package com.example.smallemailapplicationui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smallemailapplicationui.ui.theme.SmallEmailApplicationUITheme

data class Email(val id: Int, val emailAddress: String, val subject: String, val message: String)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmallEmailApplicationUITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EmailApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EmailApp() {
    var emailAddress by remember { mutableStateOf("") }
    var emailSubject by remember { mutableStateOf("") }
    var emailMessage by remember { mutableStateOf("") }
    var isEmailAddressValid by remember { mutableStateOf(true) }
    var isSubjectValid by remember { mutableStateOf(true) }
    var isMessageValid by remember { mutableStateOf(true) }
    var sentEmails by remember { mutableStateOf(listOf<Email>()) }



    val keyboardController = LocalSoftwareKeyboardController.current

    val customTextStyle = androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily.Cursive,
        fontSize = 50.sp,
        color = Color.Red
    )

    val customTextStyle2 = androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily.Serif,
        fontSize = 50.sp,
        color = Color.Blue
    )

    val customTextStyle3 = androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 25.sp,
        color = Color.Magenta
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Welcome Screen
        Text(
            text = "Welcome",
            style = customTextStyle,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = "to the",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 25.sp)

        Text(
            text = "EMAIL APP",
            style = customTextStyle2,
            modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(16.dp))

        // Email Address Input
        TextField(
            value = emailAddress,
            onValueChange = {
                emailAddress = it
                isEmailAddressValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
            },
            label = {
                Text(text = "Email Address")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            isError = !isEmailAddressValid,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    // Move focus to the next input field
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Subject Input
        TextField(
            value = emailSubject,
            onValueChange = {
                emailSubject = it
                isSubjectValid = it.isNotBlank()
            },
            label = {
                Text(text = "Subject")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            isError = !isSubjectValid,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    // Move focus to the next input field
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Message Body Input
        TextField(
            value = emailMessage,
            onValueChange = {
                emailMessage = it
                isMessageValid = it.isNotBlank()
            },
            label = {
                Text(text = "Message")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            isError = !isMessageValid,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Hide the keyboard on "Done" action
                    if (isEmailAddressValid && isSubjectValid && isMessageValid) {
                        // Add email to the list of sent emails
                        sentEmails = sentEmails + Email(
                            id = sentEmails.size + 1,
                            emailAddress = emailAddress,
                            subject = emailSubject,
                            message = emailMessage
                        )
                        // Clear input fields
                        emailAddress = ""
                        emailSubject = ""
                        emailMessage = ""
                        // Hide the keyboard
                        keyboardController?.hide()
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Send Button
        Button(
            onClick = {
                // Validate inputs and send email
                if (isEmailAddressValid && isSubjectValid && isMessageValid) {
                    // Add email to the list of sent emails
                    sentEmails = sentEmails + Email(
                        id = sentEmails.size + 1,
                        emailAddress = emailAddress,
                        subject = emailSubject,
                        message = emailMessage
                    )
                    // Clear input fields
                    emailAddress = ""
                    emailSubject = ""
                    emailMessage = ""
                    // Hide the keyboard
                    keyboardController?.hide()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "Send Email")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of Sent Emails
        Text(
            text = "Sent Emails:",
            style = customTextStyle3
        )

        Spacer(modifier = Modifier.height(8.dp))

        sentEmails.forEach { email ->
            Text(text = "Email Address: ${email.emailAddress}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Subject: ${email.subject}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Message: ${email.message}")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview(showBackground = true)
fun EmailAppPreview() {
    EmailApp()
}

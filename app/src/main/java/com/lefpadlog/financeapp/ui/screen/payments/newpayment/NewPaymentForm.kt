package com.lefpadlog.financeapp.ui.screen.payments.newpayment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lefpadlog.financeapp.code.payment.ValidatePayment
import com.lefpadlog.financeapp.ui.screen.payments.AddButton
import com.lefpadlog.financeapp.ui.screen.payments.AmountField
import com.lefpadlog.financeapp.ui.screen.payments.DateField
import com.lefpadlog.financeapp.ui.screen.payments.DescriptionField
import com.lefpadlog.financeapp.ui.screen.payments.FromField
import com.lefpadlog.financeapp.ui.screen.payments.IntervalField
import com.lefpadlog.financeapp.ui.screen.payments.TitleField
import com.lefpadlog.financeapp.ui.screen.payments.ToField
import com.lefpadlog.financeapp.ui.screen.payments.TypeField
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun NewPaymentForm(navController: NavController) {
    val check = rememberSaveable { mutableStateOf(false) }

    val type = rememberSaveable { mutableStateOf("Select") }
    val typeValid = rememberSaveable { mutableStateOf(true) }
    val interval = rememberSaveable { mutableStateOf("Once") }
    val intervalValid = rememberSaveable { mutableStateOf(true) }

    val from = rememberSaveable { mutableStateOf("Select") }
    val fromValid = rememberSaveable { mutableStateOf(true) }
    val to = rememberSaveable { mutableStateOf("Select") }
    val toValid = rememberSaveable { mutableStateOf(true) }

    val title = remember { mutableStateOf("") }
    val titleValid = rememberSaveable { mutableStateOf(true) }
    val description = remember { mutableStateOf("") }
    val descriptionValid = rememberSaveable { mutableStateOf(true) }
    val amount = remember { mutableStateOf("") }
    val amountValid = rememberSaveable { mutableStateOf(true) }
    val date = remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))) }
    val dateValid = rememberSaveable { mutableStateOf(true) }

    SideEffect {
        if (check.value) {
            titleValid.value = ValidatePayment.title(title.value)
            descriptionValid.value = ValidatePayment.description(description.value)
            dateValid.value = ValidatePayment.date(date.value)
            typeValid.value = ValidatePayment.type(type.value)
            fromValid.value = ValidatePayment.from(from.value) && ValidatePayment.fromTo(from.value, to.value)
            toValid.value = ValidatePayment.to(to.value) && ValidatePayment.fromTo(from.value, to.value)
            intervalValid.value = ValidatePayment.interval(interval.value)
            amountValid.value = ValidatePayment.amount(amount.value, from.value)
        }
    }

    Row {
        TypeField(type, typeValid)
        IntervalField(interval, intervalValid)
    }

    Row {
        FromField(type, from, fromValid)
        ToField(type, to, toValid)
    }

    Row {
        TitleField(title, titleValid)
    }

    Row {
        DescriptionField(description, descriptionValid)

        Column(
            modifier = Modifier
                .width(165.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 3.dp)
        ) {
            AmountField(amount, amountValid)
            DateField(date, dateValid)
            AddButton(
                navController, check,
                title.value, titleValid,
                description.value, descriptionValid,
                date.value, dateValid,
                type.value, typeValid,
                from.value, fromValid,
                to.value, toValid,
                interval.value, intervalValid,
                amount.value, amountValid
            )
        }
    }
}
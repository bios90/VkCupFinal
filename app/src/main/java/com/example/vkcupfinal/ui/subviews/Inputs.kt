package com.example.vkcupfinal.ui.subviews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.vkcupfinal.base.ext.let2
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupformats.ui.common.modifyIf

@Composable
fun AppInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String? = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textAlign: TextAlign = TextAlign.Start,
    bgColor: Color = AppTheme.color.Surface,
    strokeColor: Color? = AppTheme.color.TextSecondary,
    strokeWidth: Dp? = AppTheme.dimens.x025,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppTheme.dimens.x3,
        vertical = AppTheme.dimens.x2
    ),
    style: TextStyle = AppTheme.typography.RegXl.copy(
        textAlign = textAlign
    ),
    minWidth: Dp = TextFieldDefaults.MinWidth,
    minHeight: Dp = TextFieldDefaults.MinHeight,
    textColor: Color = AppTheme.color.TextPrimary,
    cursorColor: Color = AppTheme.color.TextPrimary,
    hintColor: Color = AppTheme.color.TextSecondary,
    cursorBrush: Brush? = null,
    maxLines: Int = Int.MAX_VALUE
) = BaseInput(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    hint = hint,
    maxLines = maxLines,
    visualTransformation = visualTransformation,
    textAlign = textAlign,
    bgColor = bgColor,
    strokeColor = strokeColor,
    strokeWidth = strokeWidth,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    contentPadding = contentPadding,
    shape = RoundedCornerShape(6.dp),
    style = style,
    minWidth = minWidth,
    minHeight = minHeight,
    textColor = textColor,
    cursorColor = cursorColor,
    hintColor = hintColor,
    cursorBrush = cursorBrush
)


@Composable
fun BaseInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String?,
    maxLines: Int,
    visualTransformation: VisualTransformation,
    textAlign: TextAlign,
    strokeColor: Color?,
    strokeWidth: Dp?,
    bgColor: Color,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    contentPadding: PaddingValues,
    shape: RoundedCornerShape,
    style: TextStyle,
    minWidth: Dp = TextFieldDefaults.MinWidth,
    minHeight: Dp = TextFieldDefaults.MinHeight,
    textColor: Color = AppTheme.color.TextPrimary,
    cursorColor: Color = AppTheme.color.TextPrimary,
    hintColor: Color = AppTheme.color.TextSecondary,
    cursorBrush: Brush? = null
) {
    AppTextField(
        modifier = modifier
            .clip(shape)
            .background(bgColor)
            .modifyIf(
                condition = strokeColor != null && strokeWidth != null,
                modifier = {
                    let2(strokeWidth, strokeColor) { width, color ->
                        border(width = width, color = color, shape = shape)
                    } ?: this
                }
            ),
        textStyle = style,
        visualTransformation = visualTransformation,
        value = value,
        placeholder = {
            Text(
                text = hint ?: "",
                style = AppTheme.typography.RegM.copy(
                    textAlign = textAlign,
                    color = AppTheme.color.TextSecondary
                ),
            )
        },
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            textColor = textColor,
            cursorColor = cursorColor,
            placeholderColor = hintColor,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
        ),
        maxLines = maxLines,
        singleLine = maxLines == 1,
        keyboardOptions = keyboardOptions.copy(
            capitalization = KeyboardCapitalization.Sentences
        ),
        keyboardActions = keyboardActions,
        contentPadding = contentPadding,
        minWidth = minWidth,
        minHeight = minHeight,
        cursorBrush = cursorBrush
    )
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    minWidth: Dp = TextFieldDefaults.MinWidth,
    minHeight: Dp = TextFieldDefaults.MinHeight,
    cursorBrush: Brush? = null
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class)
    BasicTextField(
        value = value,
        modifier = modifier
            .background(colors.backgroundColor(enabled).value, shape)
            .indicatorLine(enabled, isError, interactionSource, colors)
            .widthIn(min = minWidth)
            .heightIn(min = minHeight)
            .defaultMinSize(
                minWidth = minWidth,
                minHeight = minHeight
            ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = cursorBrush ?: SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = contentPadding
            )
        },
    )
}

package mx.com.hsbc.android.weather.presentation

sealed class UIEvenType {
    data object NextWeather : UIEvenType()
    class OnChangeField(val typeField: TypeField, val value: String) : UIEvenType()
}

sealed class TypeField {
    data object Location : TypeField()
    data object Temperature : TypeField()
}

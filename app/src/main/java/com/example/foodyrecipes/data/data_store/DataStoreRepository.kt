package com.example.foodyrecipes.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodyrecipes.util.Constants.DEFAULT_DIET_TYPE
import com.example.foodyrecipes.util.Constants.DEFAULT_MEAL_TYPE
import com.example.foodyrecipes.util.Constants.PREFERENCES_DIET_TYPE
import com.example.foodyrecipes.util.Constants.PREFERENCES_DIET_TYPE_ID
import com.example.foodyrecipes.util.Constants.PREFERENCES_MEAL_TYPE
import com.example.foodyrecipes.util.Constants.PREFERENCES_MEAL_TYPE_ID
import com.example.foodyrecipes.util.Constants.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_NAME
)

class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys{
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
    }

    val getMealAndDietType: Flow<MealAndDietType> = context.dataStore.data
        .catch { e ->
            if(e is IOException) emit(emptyPreferences())
            else throw e
        }.map { preferences ->
            val selectedMealTypeId = preferences[PreferencesKeys.selectedMealTypeId] ?: 0
            val selectedMealType = preferences[PreferencesKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedDietTypeId = preferences[PreferencesKeys.selectedDietTypeId] ?: 0
            val selectedDietType = preferences[PreferencesKeys.selectedDietType] ?: DEFAULT_DIET_TYPE

            MealAndDietType(selectedMealTypeId, selectedMealType, selectedDietTypeId, selectedDietType)
        }

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferencesKeys.selectedMealType] = mealType
            preferences[PreferencesKeys.selectedDietTypeId] = dietTypeId
            preferences[PreferencesKeys.selectedDietType] = dietType
        }
    }
}

data class MealAndDietType(
    val selectedMealTypeId: Int,
    val selectedMealType: String,
    val selectedDietTypeId: Int,
    val selectedDietType: String,
)
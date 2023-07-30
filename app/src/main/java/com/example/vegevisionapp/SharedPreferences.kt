import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreferences private constructor(context: Context) {
    // SharedPreferences를 사용하여 앱의 설정값을 저장하는 클래스

    // SharedPreferences 객체 선언
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // 로그인 여부를 저장하는 변수
    var isLoggedIn: Boolean
        // get() 함수: 로그인 여부를 SharedPreferences에서 가져옴
        get() = sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false)
        // set() 함수: 로그인 여부를 SharedPreferences에 저장
        set(value) {
            sharedPreferences.edit {
                putBoolean(IS_LOGGED_IN_KEY, value)
            }
        }

    // SharedPreferences 이름과 키 값 상수 선언
    companion object {
        private const val PREFS_NAME = "AppPreferences"
        private const val IS_LOGGED_IN_KEY = "isLoggedIn"

        // 싱글톤 인스턴스 생성 및 반환하는 메서드
        private var instance: AppPreferences? = null

        fun getInstance(context: Context): AppPreferences {
            if (instance == null) {
                // 인스턴스가 null일 경우 새로운 인스턴스 생성
                instance = AppPreferences(context)
            }
            return instance!!
        }
    }
}

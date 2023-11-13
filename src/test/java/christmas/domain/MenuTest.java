package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {
    @DisplayName("메뉴판에 메뉴 이름이 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프", "타파스", "시저샐러드", "티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타", "초코케이크", "아이스크림"})
    void testHasMenuName(String menuName) {
        assertThatNoException().isThrownBy(() -> {
            new Menu(menuName);
        });
    }

    @DisplayName("메뉴판에 메뉴 이름이 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"양송이", "햄버거"})
    void testHasNotMenuName(String menuName) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new Menu(menuName);
        });
    }

    @DisplayName("메뉴 이름으로 null을 전달 할 경우 예외가 발생 한다.")
    @Test
    void testHasMenuNameWithNull() {
        assertThatNullPointerException().isThrownBy(() -> {
            new Menu(null);
        });
    }
}
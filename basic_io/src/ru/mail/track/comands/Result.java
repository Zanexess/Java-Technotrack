package ru.mail.track.comands;

/**
 * В дальнейшем сделать логи на базе этого с различными кодами ошибок
 */
public class Result {
    private int errorNumber;

    // Храним номер ошибки.
    // 1   - успешное завершение
    // -1  - некорректное завершение
    // -2  - неверный ввод
    public Result (int number) {
        errorNumber = number;
    }

    public int getResult() {
        return errorNumber;
    }
}

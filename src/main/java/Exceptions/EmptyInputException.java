package Exceptions;

import Exceptions.DelphiException;

public class EmptyInputException extends DelphiException {
    public EmptyInputException() {
        super("please include a task");
    }
}

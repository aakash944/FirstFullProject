package com.example.demo.sentiment_analysis.global_handler;

import com.example.demo.sentiment_analysis.exception.PostsNotFoundException;
import com.example.demo.sentiment_analysis.exception.UserNotFoundException;
import com.example.demo.sentiment_analysis.exception.WeakPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PostsNotFoundException.class)
    public ResponseEntity<String> handlePost(PostsNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUser(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleInvalidObjectId(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid ID format");
    }

    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<String> handleWeakPassword(WeakPasswordException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
//
//        String message = ex.getBindingResult()
//                .getFieldErrors()
//                .get(0)
//                .getDefaultMessage();
//
//        return ResponseEntity.badRequest().body(message);
//    }
}

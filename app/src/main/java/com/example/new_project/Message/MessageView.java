package com.example.new_project.Message;

import com.example.new_project.BaseMVP.baseView;

public interface MessageView extends baseView {
    void Message(String res);
    void Failure(String res);
}

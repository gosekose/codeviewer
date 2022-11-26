package codeview.main.board.domain.enumtype;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AnonymousCheck {

    ON("ON"),
    OFF("OFF");

    private final String name;
}

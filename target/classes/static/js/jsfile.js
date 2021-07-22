var popupProcessingArguments = [];

const selectors = {
    POPUP_WINDOW: ".popup",
    POPUP_CONTAINER: ".base-window",
    POPUP_TITLE: "#popup-title",
    POPUP_INPUT: "#popup-input",
    POPUP_SUBMIT_BTN: "#popup-submit",
};

const UI = {
    addTask: function (name, id, completed) {
        let taskBase = document.createElement("div");
        taskBase.className = "task";
        if (completed)
            taskBase.classList.add("task-completed");

        let taskContent = document.createElement("div");
        taskContent.onclick = events.taskClick;
        taskContent.className = "task-content";
        taskContent.innerHTML = '<svg style="stroke: black; stroke-width: 2; fill:transparent;">' +
            '<circle cx="9" cy="9" r="8" /></svg>';

        let taskId = document.createElement("span");
        taskId.innerText = id;

        let taskName = document.createElement("p");
        taskName.innerText = name;

        let actions = document.createElement("ul");
        actions.className = "actions";

        let liEdit = document.createElement("li");
        liEdit.className = "edit-action";
        liEdit.onclick = events.editTask;
        liEdit.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                        <path d="M14.078 4.232l-12.64 12.639-1.438 7.129 7.127-1.438 12.641-12.64-5.69-5.69zm-10.369 14.893l-.85-.85 11.141-11.125.849.849-11.14 11.126zm2.008 2.008l-.85-.85 11.141-11.125.85.85-11.141 11.125zm18.283-15.444l-2.816 2.818-5.691-5.691 2.816-2.816 5.691 5.689z"/>
                    </svg>`;

        let liRemove = document.createElement("li");
        liRemove.className = "remove-action";
        liRemove.onclick = events.deleteTaskSubmit;
        liRemove.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                        <path d="M3 6v18h18v-18h-18zm5 14c0 .552-.448 1-1 1s-1-.448-1-1v-10c0-.552.448-1 1-1s1 .448 1 1v10zm5 0c0 .552-.448 1-1 1s-1-.448-1-1v-10c0-.552.448-1 1-1s1 .448 1 1v10zm5 0c0 .552-.448 1-1 1s-1-.448-1-1v-10c0-.552.448-1 1-1s1 .448 1 1v10zm4-18v2h-20v-2h5.711c.9 0 1.631-1.099 1.631-2h5.315c0 .901.73 2 1.631 2h5.712z"/>
                    </svg>`;

        actions.appendChild(liEdit);
        actions.appendChild(liRemove);

        taskBase.appendChild(taskContent);
        taskBase.appendChild(actions);
        taskContent.appendChild(taskName);
        taskContent.appendChild(taskId);

        $(".tasks")[0].insertBefore(taskBase, $(".add-task")[0]);
    },
    addBoard: function (name, id) {
        let boardBase = document.createElement("div");
        boardBase.className = "board";

        let actions = document.createElement("ul");
        actions.className = "actions";

        let liEdit = document.createElement("li");
        liEdit.className = "edit-action";
        liEdit.onclick = events.editBoard;
        liEdit.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                        <path d="M14.078 4.232l-12.64 12.639-1.438 7.129 7.127-1.438 12.641-12.64-5.69-5.69zm-10.369 14.893l-.85-.85 11.141-11.125.849.849-11.14 11.126zm2.008 2.008l-.85-.85 11.141-11.125.85.85-11.141 11.125zm18.283-15.444l-2.816 2.818-5.691-5.691 2.816-2.816 5.691 5.689z"/>
                    </svg>`;

        let liRemove = document.createElement("li");
        liRemove.className = "remove-action";
        liRemove.onclick = events.deleteBoardSubmit;
        liRemove.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                        <path d="M3 6v18h18v-18h-18zm5 14c0 .552-.448 1-1 1s-1-.448-1-1v-10c0-.552.448-1 1-1s1 .448 1 1v10zm5 0c0 .552-.448 1-1 1s-1-.448-1-1v-10c0-.552.448-1 1-1s1 .448 1 1v10zm5 0c0 .552-.448 1-1 1s-1-.448-1-1v-10c0-.552.448-1 1-1s1 .448 1 1v10zm4-18v2h-20v-2h5.711c.9 0 1.631-1.099 1.631-2h5.315c0 .901.73 2 1.631 2h5.712z"/>
                    </svg>`;

        actions.appendChild(liEdit);
        actions.appendChild(liRemove);
        boardBase.appendChild(actions);

        let boardContent = document.createElement("a");
        boardContent.className = "board-container";
        boardContent.href = "/boards/" + id;

        let boardName = document.createElement("p");
        boardName.innerText = name;

        boardContent.appendChild(boardName);
        boardBase.appendChild(boardContent);

        $(".boards")[0].insertBefore(boardBase, $(".add-board")[0]);
    },

    showPromptWindow: function (title, btnText, defaultValue, id, callback) {
        $(selectors.POPUP_TITLE).text(title);

        $("#editingId").text(id);
        let submitButton = $(selectors.POPUP_SUBMIT_BTN);
        let input = $(selectors.POPUP_INPUT);

        submitButton.text(btnText);
        submitButton.on("click", function () {
            submitButton.unbind("click");
            input.unbind("keypress");
            callback();
        });

        input.val(defaultValue);
        input.bind("keypress", function (e) {
            if (e.keyCode === 13) {
                callback();
                submitButton.unbind("click");
                input.unbind("keypress");
            }
        });

        $(selectors.POPUP_WINDOW).removeClass("hidden");
        input.focus();
    },
};
const events = {
    taskClick: function () {
        let task = $(this).closest(".task");
        if (task.hasClass("add-task"))
            return;
        $.post('/tasks/' + task.find("span").text() + "/toggle", function (e) {
            if (e.completeness)
                task.addClass("task-completed");
            else
            task.removeClass("task-completed");
        });
    },

    addTaskClick: function () {
        UI.showPromptWindow("Add task", "Add task", "", 0, events.addTaskSubmit);
    },
    addBoardClick: function () {
        UI.showPromptWindow("Add board", "Add board", "", 0, events.addBoardSubmit);
    },

    addTaskSubmit: function () {
        let title = $(selectors.POPUP_INPUT)[0].value;
        if (title === "")
            return;
        $.post(document.location + '/add/' + title, function (result) {
            UI.addTask(result.text, result.id, result.completeness);
        });
        $(selectors.POPUP_WINDOW).addClass("hidden");
    },
    addBoardSubmit: function () {
        let title = $(selectors.POPUP_INPUT)[0].value;
        if (title === "")
            return;
        $.post('/boards/addboard/' + title, function (result) {
            UI.addBoard(result.name, result.id);
        });
        $(selectors.POPUP_WINDOW).addClass("hidden");
    },

    editTaskSubmit: function () {
        let task = popupProcessingArguments[0];
        let id = task.find("span").text();
        let fd = new FormData();
        fd.append('name', $(selectors.POPUP_INPUT).val());

        $.ajax({
            url: '/tasks/' + id,
            data: fd,
            processData: false,
            contentType: false,
            type: 'POST',
            success: function () {
                task.find("p").text($(selectors.POPUP_INPUT).val());
            },
            error: function (res) {
                console.log(res);
            },
            complete: function () {
                $(selectors.POPUP_WINDOW).addClass("hidden");
                popupProcessingArguments = [];
            }
        });
    },
    editBoardSubmit: function () {
        let board = popupProcessingArguments[0];

        let fd = new FormData();
        fd.append('name', $(selectors.POPUP_INPUT).val());

        $.ajax({
            url: $(this).closest(".board").find("a").attr("href"),
            data: fd,
            processData: false,
            contentType: false,
            type: 'POST',
            success: function () {
                let newName = $(selectors.POPUP_INPUT).val();
                let boardNameElement = board.find("p");
                let currentBoardNameElement = $("#board-name");
                if (boardNameElement.text() === currentBoardNameElement.text())
                    currentBoardNameElement.text(newName)
                board.find("p").text(newName);
            },
            error: function (res) {
                console.log(res);
            },
            complete: function () {
                $(selectors.POPUP_WINDOW).addClass("hidden");
                popupProcessingArguments = [];
            }
        });
    },

    editTask: function () {
        let task = $(this).closest(".task");
        popupProcessingArguments = [task];
        UI.showPromptWindow("Editing task", "Submit",
            task.find("p").text(),
            task.find("span").text()
            , events.editTaskSubmit);
    },
    deleteTaskSubmit: function () {
        let task = $(this).closest(".task");
        let id = task.find("span").text();
        $.ajax({
            url: '/tasks/' + id,
            processData: false,
            contentType: false,
            type: 'DELETE',
            success: function (res) {
                task.remove();
            },
            error: function (res) {
                console.log(res);
            },
            complete: function () {
                $(selectors.POPUP_WINDOW).addClass("hidden");
            }
        });
    },

    editBoard: function () {
        let board = $(this).closest(".board");
        popupProcessingArguments = [board];
        UI.showPromptWindow("Editing board", "Submit",
            board.find("p").text(),
            0,events.editBoardSubmit);
    },
    deleteBoardSubmit: function () {
        let board = $(this).closest(".board");
        $.ajax({
            url: $(this).closest(".board").find("a").attr("href"),
            processData: false,
            contentType: false,
            type: 'DELETE',
            success: function (res) {
                board.remove();
                document.location = "/boards/"
            },
            error: function (res) {
                console.log(res);
            },
            complete: function () {
                $(selectors.POPUP_WINDOW).addClass("hidden");
            }
        });
    }
};



$(document).ready(function(){
    $(".add-task").on("click", events.addTaskClick);
    $(".add-board").on("click", events.addBoardClick);
    $(selectors.POPUP_WINDOW).on("mousedown", function (e) {
        if (e.target === e.currentTarget) {
            $(this).addClass("hidden");
            $(selectors.POPUP_SUBMIT_BTN).unbind("click");
            $(selectors.POPUP_INPUT).unbind("keypress");
        }
    });

    $("#add-board").on("click", events.addBoardSubmit);

    $("#add-task").on("click", events.addTaskSubmit);

    $(".task-content").on("click", events.taskClick);

    let tasks = $(".tasks");
    tasks.find(".edit-action").on("click", events.editTask);
    tasks.find(".remove-action").on("click", events.deleteTaskSubmit);

    let boards = $(".boards");
    boards.find(".edit-action").on("click", events.editBoard);
    boards.find(".remove-action").on("click", events.deleteBoardSubmit);

    console.log("ABOBA");
})
let books = [];

function addBook() {
    let name = document.getElementById("bookName").value;
    let author = document.getElementById("authorName").value;

    if (name === "" || author === "") {
        alert("Please fill all fields!");
        return;
    }

    books.push({ name, author, isIssued: false });
    displayBooks();
}

function displayBooks() {
    let table = document.getElementById("bookTable");
    table.innerHTML = "";

    books.forEach((book, index) => {
        table.innerHTML += `
            <tr>
                <td>${book.name}</td>
                <td>${book.author}</td>
                <td>${book.isIssued ? "Issued" : "Available"}</td>
                <td>
                    <button onclick="toggleIssue(${index})">
                        ${book.isIssued ? "Return" : "Issue"}
                    </button>
                </td>
            </tr>
        `;
    });
}

function toggleIssue(index) {
    books[index].isIssued = !books[index].isIssued;
    displayBooks();
}

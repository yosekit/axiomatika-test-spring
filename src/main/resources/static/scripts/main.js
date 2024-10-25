$(document).ready(function() {
    var currentPath = window.location.pathname;

    $('a.navbar-item').each(function() {
        var linkPath = $(this).attr('href');

        if (linkPath === currentPath) {
            $(this).addClass('active');
        }
    });


    // Выделение строк в таблице
    $("table tr").click(function() {
        $("table tr").removeClass("selected");
        $(this).addClass("selected");
    });

    // Выделение строки в таблице по ссылке
    const rowId = (new URL(location.href)).searchParams.get('row');
    if(rowId) {
        const row = $(`tr[data-id="${rowId}"]`);
        console.log("row:", row);
        console.log("row.length:", row.length);
        if (row.length) {
            row.addClass("selected");
            row[0].scrollIntoView({ behavior: "smooth", block: "center" });
        }
    }


    // Добавить "пасспорт" в поиск
    $('#addPassportBtn').click(function() {
        addTagToSearchInput('passport', 'пасспорт');
    });

    // Добавить "телефон" в поиск
    $('#addPhoneBtn').click(function() {
        addTagToSearchInput('phone', 'телефон');
    });

    // Добавить тег в поиск
    function addTagToSearchInput(type, label) {
        if ($('#searchInputContainer span[data-type="' + type + '"]').length > 0) {
            return;
        }

        var tag = $('<span>').attr('data-type', type).html(`
            ${label}<input type="text" placeholder="${label}" class="input is-small" style="width: 100px;">
            <i class="fas fa-times"></i>`);
        $('#searchInputContainer').append(tag);

        tag.find('i').click(function() {
            tag.remove();
        });
    }

    // Обработка поиска
    $('#searchButton').click(function () {
        var searchQuery = {};
        var fullName = $('#searchInput').val().trim();
        if (fullName) {
            searchQuery.fullName = fullName;
        }

        $('#searchInputContainer span').each(function() {
            var type = $(this).data('type');
            var value = $(this).find('input').val().trim();
            if (value) {
                searchQuery[type] = value;
            }
        });

        console.log('Search Query:', searchQuery);

        var queryParams = $.param(searchQuery);
        $.ajax({
            url: '/clients/search?' + queryParams,
            type: 'GET',
            success: function(response) {
                console.log('Response:', response);

                $('#clientTable tbody').empty();

                response.forEach(client => {
                    let row = `
                        <tr>
                            <td>${client.id}</td>
                            <td>${client.passport}</td>
                            <td>${client.fullName}</td>
                            <td>${client.birthdate}</td>
                            <td>${client.phone}</td>
                            <td>${client.familyStatus}</td>
                            <td>${client.employment}</td>
                            <td>
                            <div class="buttons">
                                <button class="button">
                                        <span class="icon">
                                            <i class="fas fa-edit"></i>
                                        </span>
                                </button>
                            </div>
                            </td>
                        </tr>
                    `;

                    $('#clientTable tbody').append(row);
                });
            }
        });
    });


    // Скорость пропадания уведомлений
    setTimeout(() => {
        $('.notification').fadeOut(300);
    }, 15000); // 15 sec.
});


// Для чекбокса адресов в форме /applications/create
$(document).on('change', '#copyAddress', function() {
    console.log('click');
    if ($(this).is(":checked")) {
        $("#residenceAddress").val($("#registrationAddress").val());
    } else {
        $("#residenceAddress").val("");
    }
});

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
        addTagToSearchInput('passport', '1234 556677', 'fa-passport');
    });

    // Добавить "телефон" в поиск
    $('#addPhoneBtn').click(function() {
        addTagToSearchInput('phone', '89992345678', 'fa-phone');
    });

    // Добавить тег в поиск
    function addTagToSearchInput(type, label, faIcon) {
        if ($('#searchInputContainer span[data-type="' + type + '"]').length > 0) {
            return;
        }

        var tag = $('<span>').attr('data-type', type).html(`
            <span class="icon">
              <i class="fas ${faIcon}"></i>
            </span>
            <input type="text" placeholder="${label}" class="input is-small" style="width: 100px;">
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


// Для выпадающего списка клиентов в /applications/create
$(document).on('change', "#existingClientId", function() {
    if ($(this).val() !== "") {
        $("#clientFormFields").css("display", "none");
        $("#clientFormFields").find("input, select").prop("disabled", true);
    } else {
        $("#clientFormFields").css("display", "block");
        $("#clientFormFields").find("input, select").prop("disabled", false);
    }
});

// форматирование телефона по шаблону "+7 (___) ___-__-__"
$('#phone').on('input', function () {
    let value = $(this).val().replace(/\D/g, '');

    let formattedValue = '+7 ';
    if (value.length > 1) formattedValue += `(${value.substring(1, 4)}`;
    if (value.length >= 4) formattedValue += `) ${value.substring(4, 7)}`;
    if (value.length >= 7) formattedValue += `-${value.substring(7, 9)}`;
    if (value.length >= 9) formattedValue += `-${value.substring(9, 11)}`;

    $(this).val(formattedValue);

    $('#phoneRaw').val(value);
});

// Форматирование для серии паспорта (4 цифры)
$('#series').on('input', function () {
    let input = $(this).val().replace(/\D/g, '');
    if (input.length > 4) input = input.substring(0, 4);
    $(this).val(input);
});

// Форматирование для номера паспорта (6 цифр)
$('#number').on('input', function () {
    let input = $(this).val().replace(/\D/g, '');
    if (input.length > 6) input = input.substring(0, 6);
    $(this).val(input);
});

// Форматирование для кода подразделения (000-000)
$('#subdivisionCode').on('input', function () {
    let input = $(this).val().replace(/\D/g, '');
    if (input.length > 6) input = input.substring(0, 6);

    let formattedValue = input;
    if (input.length > 3) formattedValue = `${input.substring(0, 3)}-${input.substring(3)}`;

    $(this).val(formattedValue);

    $('#subdivisionCodeRaw').val(input);
});

// Форматирование зарплаты
$('#salary').on('input', function () {
    let input = $(this).val().replace(/\D/g, '');
    if (input.length > 20) input = input.substring(0, 20);

    let formattedValue = input.replace(/\B(?=(\d{3})+(?!\d))/g, ' ');

    $(this).val(formattedValue);
    $('#salaryRaw').val(input);
});

// Форматирование суммы кредита
$('#requiredAmount').on('input', function () {
    let input = $(this).val().replace(/\D/g, '');
    if (input.length > 20) input = input.substring(0, 20);

    let formattedValue = input.replace(/\B(?=(\d{3})+(?!\d))/g, ' ');

    $(this).val(formattedValue);
    $('#requiredAmountRaw').val(input);
});
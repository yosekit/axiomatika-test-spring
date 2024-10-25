$(document).ready(function() {
    var currentPath = window.location.pathname;

    $('a.navbar-item').each(function() {
        var linkPath = $(this).attr('href');

        if (linkPath === currentPath) {
            $(this).addClass('active');
        }
    });


    // Add Passport Tag
    $('#addPassportBtn').click(function() {
        addTag('passport', 'пасспорт');
    });

    // Add Phone Tag
    $('#addPhoneBtn').click(function() {
        addTag('phone', 'телефон');
    });

    // Function to add tag
    function addTag(type, label) {
        // Check if tag already exists
        if ($('#searchInputContainer span[data-type="' + type + '"]').length > 0) {
            return; // Do not add duplicate tag
        }

        var tag = $('<span>').attr('data-type', type).html(`${label}<input type="text" placeholder="${label}" class="input is-small" style="width: 100px;"><i class="fas fa-times"></i>`);
        $('#searchInputContainer').append(tag);

        // Add event listener to remove tag
        tag.find('i').click(function() {
            tag.remove();
        });
    }

    // Search button event listener
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

});

// Для кнопки "Одобрить" в /applications
$(document).on('click', '.approve-app-btn', function() {
    var appId = $(this).data('id');

    $.ajax({
        url: '/applications/approve',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(
            {
                id: appId
            }
        ),
    });
});

// Для кнопки "Подписать" в /contracts
$(document).on('click', '.sign-contract-btn', function() {
    var contractId = $(this).data('id');

    $.ajax({
        url: '/contracts/sign',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            id: contractId
        }),
    });
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

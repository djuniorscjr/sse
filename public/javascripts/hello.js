/*! DataTables Bootstrap 3 integration
 * Â©2011-2014 SpryMedia Ltd - datatables.net/license
 */

/**
 * DataTables integration for Bootstrap 3. This requires Bootstrap 3 and
 * DataTables 1.10 or newer.
 *
 * This file sets the defaults and adds options to DataTables to style its
 * controls using Bootstrap. See http://datatables.net/manual/styling/bootstrap
 * for further information.
 */
(function(window, document, undefined){

    var factory = function( $, DataTable ) {
        "use strict";


        /* Set the defaults for DataTables initialisation */
        $.extend( true, DataTable.defaults, {
            dom:
            "<'row'<'col-s6'l><'col-s6'f>>" +
            "<'row'<'col-s11'tr>>" +
            "<'row'<'col-s5'i><'col-s7'p>>",
            renderer: 'bootstrap'
        } );


        /* Default class modification */
        $.extend( DataTable.ext.classes, {
            sWrapper:      "hoverable",
            sFilterInput:  "validate",
            sLengthSelect: ""
        } );


        /* Bootstrap paging button renderer */
        DataTable.ext.renderer.pageButton.bootstrap = function ( settings, host, idx, buttons, page, pages ) {
            var api     = new DataTable.Api( settings );
            var classes = settings.oClasses;
            var lang    = settings.oLanguage.oPaginate;
            var btnDisplay, btnClass, counter=0;

            var attach = function( container, buttons ) {
                var i, ien, node, button;
                var clickHandler = function ( e ) {
                    e.preventDefault();
                    if ( !$(e.currentTarget).hasClass('disabled') ) {
                        api.page( e.data.action ).draw( false );
                    }
                };

                for ( i=0, ien=buttons.length ; i<ien ; i++ ) {
                    button = buttons[i];

                    if ( $.isArray( button ) ) {
                        attach( container, button );
                    }
                    else {
                        btnDisplay = '';
                        btnClass = '';

                        switch ( button ) {
                            case 'ellipsis':
                                btnDisplay = '&hellip;';
                                btnClass = 'disabled';
                                break;

                            case 'first':
                                btnDisplay = lang.sFirst;
                                btnClass = button + (page > 0 ?
                                    '' : ' disabled');
                                break;

                            case 'previous':
                                btnDisplay = lang.sPrevious;
                                btnClass = button + (page > 0 ?
                                    '' : ' disabled');
                                break;

                            case 'next':
                                btnDisplay = lang.sNext;
                                btnClass = button + (page < pages-1 ?
                                    '' : ' disabled');
                                break;

                            case 'last':
                                btnDisplay = lang.sLast;
                                btnClass = button + (page < pages-1 ?
                                    '' : ' disabled');
                                break;

                            default:
                                btnDisplay = button + 1;
                                btnClass = page === button ?
                                    'active' : '';
                                break;
                        }

                        if ( btnDisplay ) {
                            node = $('<li>', {
                                'class': classes.sPageButton+' '+btnClass,
                                'id': idx === 0 && typeof button === 'string' ?
                                settings.sTableId +'_'+ button :
                                    null
                            } )
                                .append( $('<a>', {
                                    'href': '#',
                                    'aria-controls': settings.sTableId,
                                    'data-dt-idx': counter,
                                    'tabindex': settings.iTabIndex
                                } )
                                    .html( btnDisplay )
                            )
                                .appendTo( container );

                            settings.oApi._fnBindAction(
                                node, {action: button}, clickHandler
                            );

                            counter++;
                        }
                    }
                }
            };

            // IE9 throws an 'unknown error' if document.activeElement is used
            // inside an iframe or frame.
            var activeEl;

            try {
                // Because this approach is destroying and recreating the paging
                // elements, focus is lost on the select button which is bad for
                // accessibility. So we want to restore focus once the draw has
                // completed
                activeEl = $(document.activeElement).data('dt-idx');
            }
            catch (e) {}

            attach(
                $(host).empty().html('<ul class="pagination"/>').children('ul'),
                buttons
            );

            if ( activeEl ) {
                $(host).find( '[data-dt-idx='+activeEl+']' ).focus();
            }
        };


        /*
         * TableTools Bootstrap compatibility
         * Required TableTools 2.1+
         */
        if ( DataTable.TableTools ) {
            // Set the classes that TableTools uses to something suitable for Bootstrap
            $.extend( true, DataTable.TableTools.classes, {
                "container": "waves-effect",
                "buttons": {
                    "normal": "waves-effect",
                    "disabled": "disabled"
                },
                "collection": {
                    "container": "waves-effect",
                    "buttons": {
                        "normal": "waves-effect",
                        "disabled": "disabled"
                    }
                },
                "print": {
                    "info": "waves-effect"
                },
                "select": {
                    "row": "active"
                }
            } );

            // Have the collection use a bootstrap compatible drop down
            $.extend( true, DataTable.TableTools.DEFAULTS.oTags, {
                "collection": {
                    "container": "ul",
                    "button": "li",
                    "liner": "a"
                }
            } );
        }

    }; // /factory


// Define as an AMD module if possible
    if ( typeof define === 'function' && define.amd ) {
        define( ['jquery', 'datatables'], factory );
    }
    else if ( typeof exports === 'object' ) {
        // Node/CommonJS
        factory( require('jquery'), require('datatables') );
    }
    else if ( jQuery ) {
        // Otherwise simply initialise as normal, stopping multiple evaluation
        factory( jQuery, jQuery.fn.dataTable );
    }


})(window, document);



$(document).ready(function() {
    $('#table').DataTable({
        "language": {
            "lengthMenu": "Display _MENU_ records per page",
            "zeroRecords": "Não Encontrado",
            "info": "Página _PAGE_ á _PAGES_",
            "infoEmpty": "Não á registro disponível",
            "infoFiltered": "(filtrando de _MAX_ total de registro)",
            "paginate": {
                "first":      "Primeiro",
                "last":       "Último",
                "next":       "Próximo",
                "previous":   "Anterior"
            },
            "loadingRecords": "Carregando...",
            "processing":     "Processando...",
            "search":         "Buscar:"
        },
        'iDisplayLength': 5,
        'bLengthChange': false,
        'bInfo': false
    });
    if ($("#message").text().length > 0) {
        Materialize.toast($("#message").text(), 4000)
    }
    $('.datepicker').pickadate({
        selectMonths: true,
        selectYears: 1,
        // Strings and translations
        monthsFull: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
        monthsShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
        weekdaysFull: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
        weekdaysShort: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'],

        // Buttons
        today: 'Hoje',
        clear: 'Limpar',
        close: 'Fechar',

// Accessibility labels
        labelMonthNext: 'Próximo més',
        labelMonthPrev: 'Més anterior',
        labelMonthSelect: 'Selecione o més',
        labelYearSelect: 'Selecione o ano',

// Formats
        format: 'd/mm/yyyy',
        formatSubmit: undefined,
        hiddenPrefix: undefined,
        hiddenSuffix: '_submit',
        hiddenName: undefined

    });
    $('select').material_select();
    $('.modal-trigger').leanModal();
} );
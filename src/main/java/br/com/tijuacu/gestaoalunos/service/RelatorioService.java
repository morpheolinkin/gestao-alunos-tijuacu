package br.com.tijuacu.gestaoalunos.service;

import br.com.tijuacu.gestaoalunos.dto.response.AlunoResponseDTO;
import br.com.tijuacu.gestaoalunos.dto.response.MatriculaResponseDTO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    // --- GERADOR EXCEL ---
    public ByteArrayInputStream gerarExcelAlunos(List<AlunoResponseDTO> alunos) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Alunos Ativos");

            // Estilo do Cabeçalho
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Linha de Cabeçalho
            Row headerRow = sheet.createRow(0);
            String[] colunas = {"ID", "Nome Completo", "CPF", "Idade", "Sexo", "Transporte", "AEE"};
            for (int i = 0; i < colunas.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(colunas[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Linhas de Dados
            int rowIdx = 1;
            for (AlunoResponseDTO aluno : alunos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(aluno.id());
                row.createCell(1).setCellValue(aluno.nomeCompleto());
                row.createCell(2).setCellValue(aluno.cpf());
                row.createCell(3).setCellValue(aluno.idade() != null ? aluno.idade() : 0);
                row.createCell(4).setCellValue(aluno.sexo() != null ? aluno.sexo().name() : "N/I");
                row.createCell(5).setCellValue(aluno.transporteEscolar() != null ? aluno.transporteEscolar().name() : "NAO");
                row.createCell(6).setCellValue(aluno.tipoAee() != null ? aluno.tipoAee().name() : "NENHUM");
            }

            // Auto-ajuste do tamanho das colunas
            for (int i = 0; i < colunas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório Excel", e);
        }
    }

    // --- GERADOR PDF ---
    public ByteArrayInputStream gerarPdfMatriculas(List<MatriculaResponseDTO> matriculas, Integer anoLetivo) {
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Cabeçalho institucional do documento
            com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Escola Municipal Prof.ª Anísia Maria Rodrigues", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            com.lowagie.text.Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph subTitle = new Paragraph("Relatório Geral de Matrículas - Ano Letivo: " + (anoLetivo != null ? anoLetivo : "Todos"), subTitleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            subTitle.setSpacingAfter(20);
            document.add(subTitle);

            // Criação da Tabela PDF (5 colunas)
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 4, 3, 2, 2});

            // Estilo das Células do Cabeçalho da Tabela
            com.lowagie.text.Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            String[] headers = {"Nº", "Nome do Aluno", "Turma", "Data Matrícula", "Situação"};

            for (String headerText : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(headerText, tableHeaderFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                table.addCell(cell);
            }

            // Preenchimento dos dados do PDF
            com.lowagie.text.Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
            for (MatriculaResponseDTO m : matriculas) {
                table.addCell(new PdfPCell(new Phrase(m.numero() != null ? m.numero().toString() : "-", cellFont)));
                table.addCell(new PdfPCell(new Phrase(m.alunoNome(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(m.turmaNome(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(m.dataMatricula() != null ? m.dataMatricula().toString() : "-", cellFont)));

                PdfPCell situacaoCell = new PdfPCell(new Phrase(m.situacao().name(), cellFont));
                situacaoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(situacaoCell);
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException("Erro ao estruturar o arquivo PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
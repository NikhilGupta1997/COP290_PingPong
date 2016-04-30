all: DesignDoc.pdf

DesignDoc.pdf: DesignDoc.tex
	pdflatex DesignDoc.tex
	bibtex DesignDoc.aux
	#pdflatex DesignDoc.tex
	pdflatex DesignDoc.tex


clean:
	rm -rf *.aux *.log *.toc *.blg *.bbl *.out

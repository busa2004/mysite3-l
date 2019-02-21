package com.douzone.mysite.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private CommentDao commentDao;
	
	public UserVo auth(HttpServletRequest request) {
		UserVo authUser = null;
		HttpSession session = request.getSession();
		if (session != null) {
			authUser = (UserVo) session.getAttribute("authuser");
		}
		return authUser;
	}

	public void list(Model model, BoardVo vo) {
		List<BoardVo> list = null;
		int page = 0;
		int movePage = 0;
		int move = 0;
		int totalSize;
		String search = null;
		if (vo.getKwd() != null)
			search = vo.getKwd();
		if (vo.getPage() != null)
			page = vo.getPage() - 1;
		if (vo.getMovePage() != null)
			movePage = vo.getMovePage();
		if (vo.getMove() != null)
			move = vo.getMove();
		if (search != "" && search != null) {
			vo.setStart(page * 10);
			vo.setEnd(10);
			vo.setSearch(search);
			list = boardDao.getListSearch(vo);
			totalSize = boardDao.getListCount(vo).size();
		} else {
			vo.setStart(page * 10);
			vo.setEnd(10);
			list = boardDao.getList(vo);
			totalSize = boardDao.getList().size();
		}
		int totalPage = (int) (totalSize / 10);
		if (totalSize % 10 != 0) {
			totalPage += 1;
		}
		if (move == -1 && movePage + 1 <= 0) {
			move = 0;
		}
		movePage += move;
		if (movePage + 1 <= 0) {
			movePage = 0;
		} else if (movePage + 5 > totalPage) {
			movePage -= move;
		}
		model.addAttribute("list", list);
		model.addAttribute("listSize", list.size());
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalSize", totalSize);
		model.addAttribute("movePage", movePage);
		model.addAttribute("kwd", search);
	}

	public void write(HttpServletRequest request, Model model, UserVo authUser) {
		ServletContext context = request.getSession().getServletContext(); // 어플리케이션에 대한 정보를 ServletContext 객체가 갖게 됨.
		String saveDir = context.getRealPath("/Upload"); // 절대경로를 가져옴
		File Folder = new File(saveDir);

		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try {
				Folder.mkdir(); // 폴더 생성합니다.
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
		}
		int maxSize = 3 * 1024 * 1024;
		String encoding = "UTF-8";
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, saveDir, maxSize, encoding, new DefaultFileRenamePolicy());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fileName = multi.getFilesystemName("file");
		if (fileName == null) {
			fileName = "";
		}
		String div = multi.getParameter("a");
		String title = multi.getParameter("title");
		String contents = multi.getParameter("contents");
		long userno = authUser.getNo();
		BoardVo vo = null;
		if ("write".equals(div)) {
			int gno = boardDao.getMaxGno() + 1;
			int ono = 1;
			int depth = 0;
			vo = new BoardVo();
			vo.setgNo(gno);
			vo.setoNo(ono);
			vo.setDepth(depth);

		} else if ("reply".equals(div)) {
			long no = Long.parseLong(multi.getParameter("no"));
			vo = boardDao.getVo(no);
			vo.setoNo(boardDao.getMaxono(vo.getgNo()) + 1);
			vo.setDepth(vo.getDepth() + 1);
		}
		vo.setFileName(fileName);
		vo.setHit(0);
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(userno);
		boardDao.insert(vo);
		model.addAttribute("filePath", saveDir + "\\" + vo.getFileName());
		System.out.println(saveDir + "\\" + vo.getFileName());
	}

	public String view(HttpServletRequest request, HttpServletResponse response, String no, Model model) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BoardVo vo = boardDao.getVo(Long.parseLong(no));
		model.addAttribute("vo", vo);
		List<CommentVo> commentList = commentDao.getList(Long.parseLong(no));
		model.addAttribute("commentList", commentList);
		if (vo.getFileName() != null) {
			model.addAttribute("filePath", "Upload/" + vo.getFileName());
			model.addAttribute("fileName", vo.getFileName());
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				String cValue = c.getValue();
				if (cValue.equals(no)) {
					return "/board/view";
				}

			}
		}
		Cookie c = new Cookie(no, no);
		c.setComment("게시물 번호");
		c.setMaxAge(60 * 60 * 24);
		response.addCookie(c);
		boardDao.update(Long.parseLong(no));// 조회수증가
		return "/board/view";
	}

	public void reply(Long no, String title, String contents, UserVo authUser) {
		BoardVo vo = boardDao.getVo(no);
		long userno = authUser.getNo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setHit(0);
		vo.setoNo(boardDao.getMaxono(vo.getgNo()) + 1);
		vo.setDepth(vo.getDepth() + 1);
		vo.setUserNo(userno);
		boardDao.insert(vo);

	}

	public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = root + "Upload";
		String orgfilename = fileName;
		InputStream in = null;
		OutputStream os = null;
		File file = null;
		boolean skip = false;
		String client = "";
		try {
			try {
				file = new File(savePath, fileName);
				in = new FileInputStream(file);
			} catch (FileNotFoundException fe) {
				skip = true;
			}
			client = request.getHeader("User-Agent");
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "JSP Generated Data");
			if (!skip) {
				if (client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + new String(orgfilename.getBytes("KSC5601"), "ISO8859_1"));
				} else {
					orgfilename = new String(orgfilename.getBytes("utf-8"), "iso-8859-1");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + orgfilename + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				}
				response.setHeader("Content-Length", "" + file.length());
				os = response.getOutputStream();
				byte b[] = new byte[(int) file.length()];
				int leng = 0;
				while ((leng = in.read(b)) > 0) {
					os.write(b, 0, leng);
				}
			} else {
				response.setContentType("text/html;charset=UTF-8");
			}
			in.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void comment(CommentVo vo) {
		commentDao.insert(vo);
	}

	public void commentremove(Long no) {
		commentDao.delete(no);
	}

	public BoardVo modify(Long no) {
		return boardDao.getVo(no);
	}

	public void modify(BoardVo vo) {
		boardDao.update(vo);
	}

	public String remove(HttpServletRequest request, Long no) {
		BoardVo vo = boardDao.getVo(no);
		List<BoardVo> list = boardDao.getList();
		String fileName = vo.getFileName(); // 지울 파일명
		String root = request.getSession().getServletContext().getRealPath("/Upload/");
		String filePath = root + fileName;
		File f = new File(filePath); // 파일 객체생성
		if (f.exists())
			f.delete(); // 파일이 존재하면 파일을 삭제한다.
		commentDao.deleteAll(no);
		for (BoardVo v : list) {
			if (v.getgNo() == vo.getgNo() && v.getDepth() > vo.getDepth()) {
				boardDao.updateCheck(no);
				return "redirect:/board";
			}
		}
		boardDao.remove(no);
		return "redirect:/board";
	}
}
